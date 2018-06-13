(ns state.initial)

(def initial-state
  {:todos/list []
   :todos/by-id {}
   :todos/selected nil
   :todos/new-todo-form {:title nil :title-error nil :title-dirty? false
                         :body nil :body-error nil :body-dirty? false
                         :expanded? false}})
