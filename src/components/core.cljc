(ns components.core
  #?(:cljs
     (:require [reagent.core :as reagent])))

;; TODO: FIgure out how to attach the docstring as metadata on
;; the var

;; TODO: Test the actions and the selectors

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
  into the stateless component format required by reagent."
     [component injected-props]
     (fn
       ([]
        (component injected-props))
       ([props-or-child & children]
        (if (is-props? props-or-child)
          (apply component (merge injected-props props-or-child) children)
          (apply component injected-props props-or-child children))))))

#?(:cljs
   (defn- wrap-stateful-component
     "Wraps a passed in component definition in the standard format into
  the stateful component format required by reagent."
     [component initial-state injected-props]
     (fn [& args]
       (let [state (reagent/atom initial-state)]
         (fn
           ([]
            (component state injected-props))
           ([props-or-child & children]
            (if (is-props? props-or-child)
              (apply component state (merge injected-props props-or-child) children)
              (apply component state injected-props props-or-child children))))))))

(defn create-dispatcher
  [dispatch action]
  (fn [& args] (dispatch (apply action args))))

(defn get-selected-props
  [store selectors]
  (->> selectors
       (map (fn [[key selector]] [key (selector @store)]))
       (into {})))

(defn get-action-props
  [dispatch actions]
  (->> actions
       (map (fn [[key action]] [key (create-dispatcher dispatch action)]))
       (into {})))

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
         (let [selected-props (get-selected-props store selectors)
               action-props (get-action-props dispatch actions)
               injected-props (merge selected-props action-props)]
           (println action-props)
           (if initial-state
             (-> component
                 (wrap-stateful-component initial-state injected-props)
                 (with-meta other-meta))
             (-> component
                 (wrap-stateless-component injected-props)
                 (with-meta other-meta))))))))

(defmacro defcomponent
  "Provides syntactic sugar for defining components using create-component"
  [name-sym & forms]
  (cond
    (string? (first forms)) ;; Handle the docstring
    `(defcomponent ~name-sym ~@(rest forms))
    
    (map? (first forms)) ;; Handle the optional metadata
    `(def ~name-sym
       (create-component (with-meta (fn ~@(rest forms)) ~(first forms))))

    :else ;; No docstring or metadata given
    `(def ~name-sym
       (create-component (fn ~@forms)))))
