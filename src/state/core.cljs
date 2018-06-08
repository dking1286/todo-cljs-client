(ns state.core
  (:require [om.next :as om]
            [state.initial :refer [initial-state]]
            [state.queries :refer [read mutate]]))

(defonce state (atom initial-state))

(def parser (om/parser {:read read :mutate mutate}))
