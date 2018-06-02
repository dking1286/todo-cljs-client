(ns state.reducer
  (:require [state.initial :refer [initial-state]]))

(defmulti reducer
  (fn [state action] (action :type)))

(defmethod reducer :core/reset
  [state action]
  initial-state)


