(ns learn-cljs.notes.command
  (:require [learn-cljs.notes.events :refer [emit!]]
            [learn-cljs.notes.routes :as routes]
            [learn-cljs.notes.api :as api]))

(defn handle-navigate! [route-params]
  (routes/navigate! route-params))

;; Notes

(defn handle-create-note! [note]
  (api/create-note! note))

(defn handle-get-notes! [_]
  (api/get-notes!))

(defn handle-update-note! [note]
  (api/update-note! note))

(defn handle-get-note! [id]
  (api/get-note! id))

;; Tags
(defn handle-get-tags! [_]
  (api/get-tags!))

(defn handle-create-tag! [tag-name]
  (api/create-tag! tag-name))

(defn handle-tag-note! [{:keys [note-id tag-id]}]
  (api/tag-note! note-id tag-id))


(defn dispatch!
  ([command] (dispatch! command nil))
  ([command payload]
   (js/setTimeout
    #(case command
       :route/navigate (handle-navigate! payload)
       :notes/create (handle-create-note! payload)
       :notes/get-notes (handle-get-notes! payload)
       :notes/update (handle-update-note! payload)
       :notes/get-note (handle-get-note! payload)

       :tags/get-tags (handle-get-tags! payload)
       :tags/create (handle-create-tag! payload)
       :tags/tag (handle-tag-note! payload)
       (js/console.error (str "Error: unhandled command: " command))) 0)))

