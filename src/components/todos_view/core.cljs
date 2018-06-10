(ns components.todos-view.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]
            [components.todo.core :refer [Todo]]
            [components.todos-list.core :refer [todos-list]]
            [components.new-todo-form.core :refer [NewTodoForm new-todo-form]]))

(defui TodosView
  static om/IQuery
  (query [this]
    (let [todo-query (om/get-query Todo)]
      `[{:todos/list ~todo-query}]))

  Object
  (render [this]
    (let [{:keys [todos/list]} (om/props this)]
      (dom/div
       #js {:className "TodosView"}
       (todos-list list)))))

(def todos-view (om/factory TodosView))
