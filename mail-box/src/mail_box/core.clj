(ns mail-box.core
  (:gen-class))
(defn make-mailbox
  ([] (make-mailbox {:messages []
                     :next-id 1}))
  ([state]
   {:deliver!
    (fn [msg]
      (make-mailbox
       (-> state
           (update :messages conj
                   (assoc msg :read? false
                          :id (:next-id state)))
           (update :next-id inc))))
    :next-unread
    (fn []
      (when-let [msg (->> (:messages state)
                          (filter (comp not :read?))
                          (first))]
        (dissoc msg :read?)))
    :read!
    (fn [id]
      (make-mailbox
       (update state :messages
               (fn [messages]
                 (map #(if (= id (:id %)) (assoc % :read? true) %) messages)))))
    :all-messages
    (fn []
      (get state :messages))}))
(defn call [obj method & args]
  (apply (get obj method) args))

(defn test-mailbox []
  (loop [mbox (-> (make-mailbox)
                  (call :deliver! {:subject "Objects are Cool"})
                  (call :deliver! {:subject "Closures Rule"}))]
    (when-let [next-message (call mbox :next-unread)]
      (println "Got message" next-message)
      (recur
       (call mbox :read! (:id next-message)))))

  (println "Read all messages!"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (test-mailbox))
