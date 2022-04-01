(ns learn-cljs.notifications
  (:require
   [reagent.dom :as rdom]
   [goog.dom :as gdom]
   [learn-cljs.notifications.pubsub :as pubsub]
   [learn-cljs.notifications.command-event :as cmd]
   [learn-cljs.notifications.actor :as actor]))

(rdom/render
 ;; [pubsub/app]
 ;; [cmd/app]
 [actor/app]
 (gdom/getElement "app"))

(enable-console-print!)