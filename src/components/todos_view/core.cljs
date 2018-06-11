(ns components.todos-view.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]
            [components.todo.core :refer [Todo]]
            [components.todos-list.core :refer [todos-list]]
            [components.new-todo-form.core :refer [NewTodoForm new-todo-form]]))

(defui TodosView
  Object
  (render [this]
    (let [{list :todos/list todo-form :todos/new-todo-form} (om/props this)]
      (dom/div
       #js {:className "TodosView"}
       (dom/div
        #js {:className "TodosView__container"}
        (todos-list list)
        (new-todo-form todo-form))))))

(def todos-view (om/factory TodosView))
