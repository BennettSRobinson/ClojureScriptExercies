(ns learn-cljs.async
  (:require
   [goog.dom :as gdom]
   [clojure.core.async :refer [go >!]]))

(go (println "Hello World"))
(go (loop []
      (let [val (<! in-ch)]
        (when (pred? val)
          (>! out-ch val)))
      (recur)))