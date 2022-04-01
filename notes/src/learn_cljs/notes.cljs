(ns learn-cljs.notes
  (:require
   [learn-cljs.notes.ui.header :refer [header]]
   [learn-cljs.notes.ui.main :refer [main]]
   [learn-cljs.notes.ui.sidebar :refer [sidebar]]
   [learn-cljs.notes.ui.footer :refer [footer]]
   [learn-cljs.notes.routes :as routes]
   [learn-cljs.notes.event-handlers.core]
   [goog.dom :as gdom]
   [reagent.dom :as rdom]))

(defonce initialized?
  (do
    (routes/initialize!)
    true))

(defn app []
  [:div.app
   [header]
   [main]
   [sidebar]
   [footer]])

(rdom/render
 [app]
 (gdom/getElement "app"))