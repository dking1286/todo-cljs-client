(ns components.todos-view.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [components.core :refer-macros [defcomponent]]
            [components.todo-list.core :refer [todo-list]]
            [components.new-todo-form.core :refer [new-todo-form]]))

(defn todos-view
  "View representing the below-the-fold part of the todo list view."
  [props & children]
  [:div.todos-view
   [todo-list]
   [new-todo-form]])
