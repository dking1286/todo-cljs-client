(ns state.reducer
  (:require [state.initial :refer [initial-state]]))

(defmulti reducer (fn [state action] (action :type)))

(defmethod reducer :reset
  [state action]
  initial-state)

(defmethod reducer :todos/add
  [state {:keys [payload]}]
  (let [todo (payload :todo)]
    (-> state
        (update :todos/by-id assoc (todo :id) todo)
        (update :todos/list conj {:id todo}))))

(defmethod reducer :todos/select
  [state {:keys [payload]}]
  (let [{:keys [id]} payload]
    (-> state
        (assoc :todos/selected {:id id}))))

(defmethod reducer :todos/mark-done
  [state {:keys [payload]}]
  (let [id (payload :id)]
    (-> state
        (update-in [:todos/by-id id] assoc :done? true))))

(defmethod reducer :todos/mark-undone
  [state {:keys [payload]}]
  (let [id (payload :id)]
    (-> state
        (update-in [:todos/by-id id] assoc :done? false))))

(defmethod reducer :todos/delete
  [state {:keys [payload]}]
  (let [{:keys [id]} payload]
    (-> state
        (update :todos/by-id dissoc id))))
