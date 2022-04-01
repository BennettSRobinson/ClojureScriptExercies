(ns learn-cljs.chat
  (:require
   [learn-cljs.chat.state :as state]
   [learn-cljs.chat.message-bus :as bus]
   [learn-cljs.chat.components.app :refer [init-app]]
   [learn-cljs.chat.handlers]
   [goog.dom :as gdom]
   [learn-cljs.chat.api :as api]))

(def initialized?
  (do
    (api/init! bus/msg-ch "ws://localhost:9500/figwheel-connect?fwprocess=6b2e5c&fwbuild=dev&fwsid=b58aa3c8-efec-4bbb-b312-5d96ddcec1c7&fwsname=Jazmin ")
    (init-app
     (gdom/getElement "app")
     bus/msg-ch)
    (set! (.-getAppState js/window) #(clj->js @state/app-state))
    true))

(js/console.log "hello world")