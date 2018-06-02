(ns state.core
  (:require [reagent.core :as reagent]
            [state.initial :refer [initial-state]]
            [state.reducer :refer [reducer]]
            [state.middleware.logging :refer [wrap-logging]]
            [utils.function :as f]))

(defonce store
  (reagent/atom initial-state))

(defn- dispatch*
  [action]
  (swap! store reducer action))

(def middleware-stack
  [wrap-logging])

(def middleware
  (->> middleware-stack
       (map (f/call-with store))
       (apply comp)))

(def dispatch
  (middleware dispatch*))
