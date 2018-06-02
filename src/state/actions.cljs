(ns state.actions)

(defn action-creator
  [type]
  (fn
    ([] {:type type})
    ([opts] (merge opts {:type type}))))
