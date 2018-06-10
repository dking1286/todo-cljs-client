(ns resources.todos.reads
  (:require [state.queries :refer [read]]))

(defmethod read :todos/list
  [{:keys [state]} _ params]
  (let [{:keys [todos/list todos/by-id]} @state]
    {:value (into [] (map #(get-in by-id %) list))}))

(defmethod read :todos/by-id
  [{:keys [state]} _ {:keys [id]}]
  {:value (get-in @state [:todos/by-id id])})
