(ns resources.todos.reads
  (:require [state.queries :refer [read]]))

(defmethod read :todos/list
  [{:keys [state]} _ params]
  (let [{:keys [todos/list] :as st} @state]
    {:value (into [] (map #(get-in st %) list))}))

(defmethod read :todos/by-id
  [{:keys [state]} _ {:keys [id]}]
  {:value (get-in @state [:todos/by-id id])})

(defmethod read :todos/new-todo-form
  [{:keys [state]} _ params]
  {:value (@state :todos/new-todo-form)})
