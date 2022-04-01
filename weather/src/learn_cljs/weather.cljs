(ns ^:figwheel-hooks learn-cljs.weather
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [ajax.core :as ajax]))

(defonce app-state (atom {:title "WhichWeather"
                          :lat nil
                          :long nil
                          :temperatures {:today {:label "Today"
                                                 :value nil}
                                         :tomorrow {:label "Tomorrow"
                                                    :value nil}}}))
(def api-key "39bc38b19474cc3c7193a2d08941a660")

(defn handle-response [resp]
  (let [today (get-in resp ["list" 0 "main" "temp"])
        tomorrow (get-in resp ["list" 8 "main" "temp"])]
    (swap! app-state
           update-in [:temperatures :today :value] (constantly today))
    (swap! app-state
           update-in [:temperatures :tomorrow :value] (constantly tomorrow))))

(defn get-forcast!  []
  (let [lat (:lat @app-state) lon (:long @app-state)]
    (ajax/GET "https://api.openweathermap.org/data/2.5/forecast"
      {:params {"lat" lat
                "lon" lon
                "appid" api-key
                "units" "imperial"}
       :handler handle-response})))

(defn title []
  [:h1 (:title @app-state)])

(defn temperature [temp]
  [:div {:class "temperature"}
   [:div {:class "value"}
    (:value temp)]
   [:h2 (:label temp)]])

(defn postal-code []
  [:div {:class "postal-code"}
   [:h3 "Enter your postal code"]
   [:input {:type "number"
            :placeholder "Longitude"
            :value (:long @app-state)
            :on-change #(swap! app-state assoc :long (-> % .-target .-value))}]
   [:input {:type "number"
            :placeholder "Latitude"
            :value (:lat @app-state)
            :on-change #(swap! app-state assoc :lat (-> % .-target .-value))}]
   [:button {:on-click get-forcast!} "Go"]])

(defn app []
  [:div {:class "app"}
   [title]
   [:div {:class "temperatures"}
    (for [temp (vals (:temperatures @app-state))]
      [temperature temp])]
   [postal-code]])
;; (defn hello-world []
;;   [:div
;;    [:h1 {:class "app-title" :id "title"} "Hello, World"]])

(defn mount-app-element []
  (rdom/render [app] (gdom/getElement "app")))
;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for  testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
