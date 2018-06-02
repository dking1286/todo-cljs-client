(ns state.middleware.logging)

(defonce action-history
  (atom []))

(defn wrap-logging
  [store]
  (fn [next]
    (fn [action]
      (swap! action-history conj action)
      (println action)
      (next action)
      (println @store))))
