(ns cljs.user
  (:require [clojure.pprint :refer [pprint]]
            [state.core :refer [store dispatch]]
            [state.middleware.logging :refer [toggle-logging]]
            [resources.todos.actions :as todo-actions]
            [resources.todos.selectors :as todos-selectors]))

(defn get-todos
  []
  (todos-selectors/get-todos @store))
