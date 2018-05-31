(ns components.root.core
  (:require [state.core :refer [store]]))

(defn root
  "Root view component of the todo list application"
  []
  [:div {} (@store :greeting)])
