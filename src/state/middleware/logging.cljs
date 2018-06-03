(ns state.middleware.logging
  (:require [clojure.pprint :refer [pprint]]))

(defonce action-history
  (atom []))

(defonce logging-activated
  (atom false))

(defn toggle-logging
  []
  (swap! logging-activated not))

(defn wrap-logging
  [store]
  (fn [next]
    (fn [action]
      (swap! action-history conj action)
      (when @logging-activated
        (pprint action))
      (next action)
      (when @logging-activated
        (pprint @store)))))
