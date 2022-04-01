(ns learn-cljs.notes.ui.header
  (:require [learn-cljs.notes.ui.common :refer [button]]))

(defn header []
  [:header.page-header
   [button "+ New Note"
    {:route-params [:create-note]
     :class "inverse"}]])