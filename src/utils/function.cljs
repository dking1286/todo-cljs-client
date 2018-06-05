(ns utils.function)

(defn call-with
  [& args]
  (fn [f]
    (apply f args)))

(defn memoize-last-args
  [f]
  (let [previous (atom {:args [] :return nil})]
    (fn [& args]
      (if (= args (@previous :args))
        (@previous :return)
        (let [return (apply f args)]
          (swap! previous (constantly {:args args :return return}))
          return)))))
