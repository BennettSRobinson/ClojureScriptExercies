(ns learn-cljs.notes.events)

(def listeners (atom []))

(defn emit!
  ([type] (emit! type nil))
  ([type payload]
   (doseq [listen-fn @listeners]
     (listen-fn type payload))))

(defn register-listener! [listen-fn]
  (swap! listeners conj listen-fn))