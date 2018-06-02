(ns resources.todos.selectors
  (:require [state.selectors :refer-macros [defselector]]))

(defn get-todos-list
  [state]
  (state :todos/list))

(defn get-todos-by-id
  [state]
  (state :todos/by-id))

(defselector get-todos
  [get-todos-list get-todos-by-id]
  (fn [list by-id]
    (into [] (comp (map :id) (map by-id)) list)))
