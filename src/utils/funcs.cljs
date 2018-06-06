(ns utils.funcs)

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

(defn transform-preserve-meta
  [obj f]
  (let [in-meta (meta obj)
        out (f obj)
        out-meta (meta out)]
    (with-meta out (merge in-meta out-meta))))

(defn comp-preserve-meta
  [& fs]
  (fn [obj]
    (->> (reverse fs)
         (reduce transform-preserve-meta obj))))
