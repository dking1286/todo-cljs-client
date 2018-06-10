(ns cljs.user
  (:require [clojure.pprint :refer [pprint]]
            [state.core]))

(defn swap-state!
  [f & args]
  (apply swap! state.core/state f args))

