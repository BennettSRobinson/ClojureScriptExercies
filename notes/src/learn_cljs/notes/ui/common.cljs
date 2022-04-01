(ns learn-cljs.notes.ui.common
  (:require [learn-cljs.notes.command :refer [dispatch!]]
            [learn-cljs.notes.state :as state]
            [learn-cljs.notes.routes :as routes]))

(defn handle-navigate [route-params]
  (fn [_]

    (dispatch! :route/navigate route-params)))
(defn handle-dispatch [command-data]
  (fn [e]
    (.preventDefault e)
    (apply dispatch! command-data)))


(defn button [text {:keys [route-params dispatch on-click class]
                    :or {class ""}}]
  [:button {:class (str "button " class)
            :on-click (cond
                        route-params (handle-navigate route-params)
                        dispatch (handle-dispatch dispatch)
                        on-click on-click
                        :else #(js/console.error "No action provided for button"))}
   text])

(defn link [text route-params]
  [:a {:href (routes/get-url route-params)
       :on-click (handle-navigate route-params)
       :class (if (routes/matches? route-params (:current-route @state/app))
                "active" "")}
   text])



