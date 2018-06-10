(ns components.todos-list.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [components.todo.core :refer [todo]]))

(defui TodosList
  Object
  (render [this]
    (let [list (om/props this)]
      (dom/ul 
       #js {:className "TodosList"}
       (for [todo* list]
         (todo todo*))))))

(def todos-list (om/factory TodosList))
