(ns resources.todos.reducer
  (:require [state.reducer :refer [reducer]]))

(defmethod reducer :todos/add
  [state {todo :payload}]
  (-> state
      (update :todos/by-id assoc (todo :id) (merge {:done? false} todo))
      (update :todos/list conj {:id (todo :id)})))

(defmethod reducer :todos/select
  [state {id :payload}]
  (-> state
      (assoc :todos/selected {:id id})))

(defmethod reducer :todos/mark-done
  [state {id :payload}]
  (-> state
      (update-in [:todos/by-id id] assoc :done? true)))

(defmethod reducer :todos/mark-undone
  [state {id :payload}]
  (-> state
      (update-in [:todos/by-id id] assoc :done? false)))

(defmethod reducer :todos/delete
  [state {id :payload}]
  (-> state
      (update :todos/by-id dissoc id)))
