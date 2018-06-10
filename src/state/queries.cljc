(ns state.queries)

(defmulti read (fn [env key params] key))

(defmethod read :greeting
  [{:keys [state]} key params]
  {:value (@state :greeting)})

(defmulti mutate (fn [env key params] key))

(defmethod mutate 'change-greeting
  [{:keys [state]} key {:keys [greeting]}]
  {:value {:keys [:greeting]}
   :action #(swap! state assoc :greeting greeting)})
