(ns state.core
  (:require [reagent.core :as reagent]
            [state.initial :refer [initial-state]]))

(defonce store (reagent/atom initial-state))
