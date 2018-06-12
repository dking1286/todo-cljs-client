(ns state.queries
  (:refer-clojure :exclude [read]))

(defmulti read (fn [env key params] key))

(defmethod read :default
  [{:keys [state]} key params]
  {:value (key @state)})

(defmulti mutate (fn [env key params] key))
