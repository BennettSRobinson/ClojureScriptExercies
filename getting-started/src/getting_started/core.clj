(ns getting-started.core
  (:gen-class))

(def knight-awake? false)
(def archer-awake? true)
(def prisoner-awake? false)
(def dog-present? false)

(defn can-fast-attack? [state]
  (not state))

(defn can-spy? [knight archer prisoner]
  (or knight archer prisoner))

(defn can-signal-prisoner? [archer prisoner]
  (and (true? prisoner) (false? archer)))

(defn can-free-prisoner? [k a p d]
  (if d
    (if (false? a)
      true
      false)
    (if (and (true? p) (false? k) (false? a))
      true
      false)))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (can-free-prisoner? knight-awake? archer-awake? prisoner-awake? dog-present?)))
