(ns ^:figwheel-hooks learn-cljs.contacts
  (:require-macros [hiccups.core :as hiccups])
  (:require
   [goog.dom :as gdom]
   [hiccups.runtime]
   [clojure.string :as str]
   [goog.events :as gevents]))

(def contact-list [])

(def initial-state
  {:contacts contact-list
   :selected nil
   :editing? false})

(defn make-address [address]
  (select-keys address [:select :city :state :country]))
(defn maybe-set-address [contact]
  (if (:address contact)
    (update contact :address make-address)
    contact))

;; (defn make-contact [contact]
;;   (let [clean-contact (select-keys contact [:first-name :last-name :email])]
;;     (if-let [address (:address contact)]
;;       (assoc clean-contact :address (make-address address))
;;       clean-contact)))

(defn make-contact [contact]
  (-> contact
      (select-keys [:first-name :last-name :email])
      (maybe-set-address)))
(defn add-contact [contact-list input]
  (conj contact-list
        (make-contact input)))

(defn remomve-contact [contact-list idx]
  (vec
   (concat
    (subvec contact-list 0 idx)
    (subvec contact-list (inc idx)))))

(defn replace-contact [contact-list idx input]
  (assoc contact-list idx (make-contact input)))

(defn format-name [contact]
  (->> contact
       ((juxt :first-name :last-name))
       (str/join " ")))

(defn delete-icon [idx]
  [:span {:class "delete-icon" :data-idx idx}
   [:span {:class "mu mu-delete"}]])

(defn render-contact-list-item [idx contact selected?]
  [:div {:class (str "card contact-summary" (when selected? " selected"))
         ::data-idx idx}
   [:div {:class "card-content"}
    [:div {:class "level"}
     [:div {:class "level-left"}
      [:div {:class "level-item"}
       (delete-icon idx)
       (format-name contact)]]
     [:div {:class "level-right"}
      [:span {:class "mu mu-right"}]]]]])

(defn set-app-html! [html-str]
  (set! (.-innerHTML app-container) html-str))

(defn render-app! [state]
  (set-app-html!
   (hiccups/html
    [:div])))


(defn refresh! [state]
  (render-app! state)
  (attach-event-handlers! state))

(defn on-open-contact [e state]
  (refresh!
   (let [idx (int (.. e -currentTarget -dataset -idx))]
     (assoc state ::selected idx
            ::editing? true))))

(defn attach-event-handlers! [state]
  (doseq [elem (array-seq (gdom/getElementsByClass "contact-summary"))]
    (gevents/listen elem "click"
                    (fn [e] (on-open-contact e state)))))
(refresh! initial-state)

(defn render-contact-list [state]
  (let [{:keys [:contacts :selected]} state]))

(defn get-app-element []
  (gdom/getElement "app"))



;; specify reload hook with ^:after-load metadata


(defn ^:after-load on-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
