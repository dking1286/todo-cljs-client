(ns cljs.user
  (:require [clojure.pprint :refer [pprint]]
            [state.core :refer [state parser]]
            [state.initial :refer [initial-state]]))

(defn get-state
  []
  @state)

(defn swap-state!
  [f & args]
  (apply swap! state f args))

(defn reset-state!
  []
  (reset! state initial-state))

(defn parse
  [q]
  (parser {:state state} q))

