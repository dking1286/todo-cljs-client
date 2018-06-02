(ns components.todos-view.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [components.core :refer-macros [defcomponent]]
            [components.todo-list.core :refer [todo-list]]
            [components.new-todo-form.core :refer [new-todo-form]]
            [components.primary-button.core :refer [primary-button]]))

(defcomponent todos-view
  "View representing the below-the-fold part of the todo list view."
  [{:keys [todos]} & children]
  [:div.todos-view
   [todo-list]
   [new-todo-form]])
