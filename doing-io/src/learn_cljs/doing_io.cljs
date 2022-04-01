(ns ^:figwheel-hooks learn-cljs.doing-io
  (:require
   [goog.dom :as gdom]
   [goog.events :as events]))

(defn same-password? [ps1 ps2]
  (= (aget ps1 "value")
     (aget ps2 "value")))

(defn handle-change [ps1 ps2 status]
  (gdom/setTextContent status
                       (if (same-password? ps1 ps2)
                         "Matches"
                         "Do Not Match")))
(let [password (gdom/createElement "input")
      confirm (gdom/createElement "input")
      status (gdom/createElement "p")
      app (gdom/getElement "app")]
  (gdom/setProperties password #js {"type" "password"})
  (gdom/setProperties confirm #js {"type" "password"})

  (events/listen password "keyup" #(handle-change password confirm status))
  (events/listen confirm "keyup" #(handle-change password confirm status))

  (gdom/setTextContent app "")
  (gdom/appendChild app password)
  (gdom/appendChild app confirm)
  (gdom/appendChild app status))