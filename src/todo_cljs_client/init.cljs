(ns todo-cljs-client.init
  (:require [state.core :refer [dispatch]]))

(defonce initialized? (atom false))

(defn on-init
  "Initializes the application. Is only called one time, even when code is
  reloaded in development mode by figwheel."
  []
  (when-not @initialized?
    (do
      (println "Todo cljs initialized")
      (swap! initialized? (constantly true)))))
