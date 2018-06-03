(ns state.actions)

(defn action-creator
  [type]
  (fn
    ([] {:type type})
    ([payload] {:type type :payload payload})
    ([payload opts] (merge opts {:type type :payload payload}))))
