(ns components.core
  #?(:cljs
     (:require [reagent.core :as reagent])))

;; TODO: FIgure out how to attach the docstring as metadata on
;; the var

;; TODO: Handle the actions and selectors

(def reserved-metadata-keys
  [:initial-state :selectors :actions :store :dispatch])

(defn- get-reserved-meta
  "Gets the reserved portion of a map of metadata, according to the
  reserved metadata keys in the vector above."
  [metadata]
  (->> reserved-metadata-keys
       (map (fn [key] [key (metadata key)]))
       (into {})))

(defn- get-unreserved-meta
  "Gets the unreserved portion of a map of metadata, according to the
  reserved metadata keys above."
  [metadata]
  (apply dissoc metadata reserved-metadata-keys))

(defn- parse-component-meta
  "Parses a component's metadata into
  reserved and unreserved portions."
  [metadata]
  (-> metadata
      get-reserved-meta
      (assoc :other-meta (get-unreserved-meta metadata))))

(defn- is-props?
  "Determines whether or not a thing is a props object."
  [thing]
  (map? thing))

#?(:cljs
   (defn- wrap-stateless-component
     "Wraps a passed in component definition in the standard format
  into the usual format required by reagent."
     [component]
     (fn
       ([]
        (component nil))
       ([props-or-child & children]
        (if (is-props? props-or-child)
          (apply component props-or-child children)
          (apply component nil props-or-child children))))))

#?(:cljs
   (defn- wrap-stateful-component
     "Wraps a passed in component definition in the standard format into
  the usual format required by reagent."
     [component initial-state]
     (fn [& args]
       (let [state (reagent/atom initial-state)]
         (fn
           ([]
            (component state nil))
           ([props-or-child & children]
            (if (is-props? props-or-child)
              (apply component state props-or-child children)
              (apply component state nil props-or-child children))))))))


#?(:clj
   (declare create-component)

   :cljs
   (defn create-component
     "Function that normalizes component definitions into a
  common signature, (fn [props & children]). This will typically not be called
  directly, use defcomponent instead."
     [component]
     (let [{:keys [initial-state store dispatch selectors actions other-meta]}
           (some-> component meta parse-component-meta)]
       (cond
         (and selectors (nil? store))
         (throw (new js/TypeError "No store provided for selectors"))

         (and actions (nil? dispatch))
         (throw (new js/TypeError "No dispatch provided for actions"))

         :else
         (if initial-state
           (-> component
               (wrap-stateful-component initial-state)
               (with-meta other-meta))
           (-> component
               wrap-stateless-component
               (with-meta other-meta)))))))

(defmacro defcomponent
  "Provides syntactic sugar for defining components using create-component"
  [name-sym & forms]
  (cond
    (string? (first forms))
    `(defcomponent ~name-sym ~@(rest forms))
    
    (map? (first forms))
    `(def ~name-sym
       (create-component (with-meta (fn ~@(rest forms)) ~(first forms))))

    :else
    `(def ~name-sym
       (create-component (fn ~@forms)))))
