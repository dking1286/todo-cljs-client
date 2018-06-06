(ns state.selectors
  #?(:cljs (:require [utils.funcs :refer [memoize-last-args]]))
  #?(:cljs (:require-macros [state.selectors :refer  [defselector]])))

#?(:cljs
   (defn create-selector
      [inputs output]
      (let [memoized-output (memoize-last-args output)]
        (fn [state]
          (let [input-values (map (fn [f] (f state)) inputs)]
            (apply memoized-output input-values))))))

#?(:clj
   (defmacro defselector
     [name-sym & forms]
     `(def ~name-sym (create-selector ~@forms))))
