(ns components.root.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]
            [components.navbar.core :refer [navbar]]
            [components.todos-view.core :refer [todos-view]]
            [components.todo.core :refer [Todo]]
            [components.new-todo-form.core :refer [NewTodoForm]]))

(defui Root
  static om/IQuery
  (query [this]
    `[{:todos/list ~(om/get-query Todo)}
      {:todos/new-todo-form ~(om/get-query NewTodoForm)}])

  Object
  (render [this]
    (mui/mui-theme-provider
     (dom/div
      #js {:className "Root"}
      (navbar)
      (todos-view (om/props this))))))

(def root (om/factory Root))
