(ns resources.todos.actions
  (:require [state.actions :refer [action-creator]]))

(def add (action-creator :todos/add))
(def select (action-creator :todos/select))
(def mark-done (action-creator :todos/mark-done))
(def mark-undone (action-creator :todos/mark-undone))
(def delete (action-creator :todos/delete))

