(ns components.todo-list.core
  (:require [state.core :refer [store dispatch]]
            [state.selectors :refer [get-todos]]))

(defn todo-list
  []
  (let [todos (get-todos @store)]
    [:div
     (map :title todos)]))
