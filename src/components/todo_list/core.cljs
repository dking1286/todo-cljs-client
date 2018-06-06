(ns components.todo-list.core
  (:require [state.core :refer [store dispatch]]
            [resources.todos.selectors :as todos-selectors]
            [resources.todos.actions :as todos-actions]
            [components.core :refer-macros [defcomponent]]
            [components.todo.core :refer [todo]]))

(defcomponent todo-list
  "View component representing a list of todo items."
  {:store store
   :selectors {:todos todos-selectors/get-todos}}
  [{:keys [todos]} & children]
  (if (empty? todos)
    [:div.todo-list.empty
     "No todos yet..."]
    [:div.todo-list
     (map-indexed (fn [i todo*] [todo {:todo todo* :key i}]) todos)]))
