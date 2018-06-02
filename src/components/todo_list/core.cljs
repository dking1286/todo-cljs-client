(ns components.todo-list.core
  (:require [resources.todos.selectors :as todos-selectors]
            [components.core :refer-macros [defcomponent]]))

(defcomponent todo-list
  "View component representing a list of todo items."
  [{:keys [todos]} & children]
  [:div.todo-list
   "Todo list"])

