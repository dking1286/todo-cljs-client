(ns state.core
  (:require [reagent.core :as reagent]
            [state.initial :refer [initial-state]]
            [state.reducer :refer [reducer]]))

(defonce store (reagent/atom initial-state))

(defn dispatch
  [action]
  (swap! store reducer action))

