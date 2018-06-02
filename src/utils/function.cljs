(ns utils.function)

(defn call-with
  [& args]
  (fn [f]
    (apply f args)))
