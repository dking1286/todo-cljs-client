(ns state.selectors
  #?(:cljs
     (:require-macros [state.selectors :refer  [defselector]])))

(defn- memoize-last-args
  [f]
  (let [previous (atom {:args [] :return nil})]
    (fn [& args]
      (if (= args (@previous :args))
        (@previous :return)
        (let [return (apply f args)]
          (swap! previous (constantly {:args args :return return}))
          return)))))

(defn create-selector
  [inputs output]
  (let [memoized-output (memoize-last-args output)]
    (fn [state]
      (let [input-values (map (fn [f] (f state)) inputs)]
        (apply memoized-output input-values)))))

#?(:clj
   (defmacro defselector
     [name-sym & forms]
     `(def ~name-sym (create-selector ~@forms))))
