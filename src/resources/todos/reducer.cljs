(ns resources.todos.reducer
  (:require [state.reducer :refer [reducer]]))

(defmethod reducer :todos/add
  [{:keys [payload]} state]
  )
