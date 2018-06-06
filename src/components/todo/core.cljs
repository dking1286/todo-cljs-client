(ns components.todo.core
  (:require [state.core :refer [store dispatch]]
            [resources.todos.actions :as todos-actions]
            [components.core :refer-macros [defcomponent]]))

(defcomponent todo
  "View component representing a single item on the todo list"
  {:dispatch dispatch
   :actions {:mark-done todos-actions/mark-done
             :mark-undone todos-actions/mark-undone
             :select todos-actions/select}}
  [{:keys [todo mark-done mark-undone select]}]
  [:div.todo
   (todo :title)])
