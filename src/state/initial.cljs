(ns state.initial)

(def initial-state
  {:todos/list []
   :todos/by-id {}
   :todos/selected nil
   :todos/new-todo-form {:title nil :body nil :expanded? false}})
