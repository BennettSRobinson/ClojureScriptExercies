(ns learn-cljs.notes.ui.views.note-form
  (:require [reagent.core :as r]
            [learn-cljs.notes.state :refer [app]]
            [learn-cljs.notes.ui.common :refer [button]]
            [learn-cljs.notes.ui.tags :refer [tag-selector]]))

(defn update-data [data key]
  (fn [e]
    (swap! data assoc key (.. e -target -value))))

(defn is-new? [data]
  (-> data :id nil?))

(defn input [data key label]
  (let [id (str "filed-" (name key))]
    [:div.field
     [:div.label
      [:label {:for id} label]]
     [:div.control
      [:input {:id id
               :type "text"
               :on-change (update-data data key)
               :value (get @data key "")}]]]))

(defn textarea [data key label]
  (let [id (str "field-" (name key))]
    [:div.field
     [:div.label
      [:label {:for id} label]]
     [:div.control
      [:textarea {:id id
                  :on-change (update-data data key)
                  :value (get @data key "")}]]]))
(defn submit-button [data]
  (let [[action text] (if (is-new? @data)
                        [:notes/create "Create"]
                        [:notes/update "Save"])]
    [button text {:dispatch [action @data]}]))

(defn note-form []
  (let [form-data (r/cursor app [:note-form])]
    (fn []
      [:section.note-form
       [:h2.page-title
        (if (is-new? @form-data) "New Note" "Edit Note")]
       [:form
        [input form-data :title "Title"]
        [textarea form-data :content "Content"]
        [submit-button form-data]]
       [:div.tags
        [:h3 "Tags"]
        (if (is-new? @form-data)
          [:p.help "Please save your note before adding tags"]
          [tag-selector])]])))