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

(defn- create-selector
  [inputs output]
  (let [memoized-output (memoize-last-args output)]
    (fn [state]
      (let [input-values (map (fn [f] (f state)) inputs)]
        (apply memoized-output input-values)))))

#?(:clj
   (defmacro defselector
     [name-sym & forms]
     `(def ~name-sym (create-selector ~@forms))))

(defn get-todos-list
  [state]
  (state :todos/list))

(defn get-todos-by-id
  [state]
  (state :todos/by-id))

(defn get-todos-selected
  [state]
  (state :todos/selected))

(defselector get-todos
  [get-todos-list get-todos-by-id]
  (fn [todos-list todos-by-id]
    (->> todos-list (map :id) (map todos-by-id) (into []))))

(defselector get-selected
  [get-todos-selected get-todos-by-id]
  (fn [{:keys [id]} todos-by-id]
    (todos-by-id id)))
