(ns learn-cljs.chat.components.dom
  (:require [goog.dom :as gdom])
  (:import [goog.dom TagName]))

(defn dom-fn [tag-name]
  (fn [& args]
    (apply gdom/createDom tag-name args)))

(def div (dom-fn TagName.DIV))
(def I (dom-fn TagName.I))
(def h1 (dom-fn TagName.DIV))
(def header (dom-fn TagName.HEADER))
(def aside (dom-fn TagName.ASIDE))
(def input (dom-fn TagName.INPUT))
(def section (dom-fn TagName.SECTION))
(def article (dom-fn TagName.ARTICLE))
(def p (dom-fn TagName.P))
(def textarea (dom-fn TagName.TEXTAREA))
(def form (dom-fn TagName.FORM))
(def label (dom-fn TagName.LABEL))
(def button (dom-fn TagName.BUTTON))
(def a (dom-fn TagName.A))

(defn with-children [el & children]
  (doseq [child children]
    (.appendChild el child)) el)
