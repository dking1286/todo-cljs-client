(ns components.new-todo-form.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]))

(defui NewTodoForm)

(def new-todo-form (om/factory NewTodoForm))
