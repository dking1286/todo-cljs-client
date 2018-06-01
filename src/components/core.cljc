(ns components.core)

(def reserved-metadata-keys
  [:selectors :actions :store :dispatch])

(defn- parse-component-meta
  [metadata]
  (let [parsed-metadata (into {} (map metadata) reserved-metadata-keys)]
    (assoc parsed-metadata :other-meta (apply dissoc metadata reserved-metadata-keys))))

(defn- is-props?
  "Determines whether or not a thing is a props object."
  [thing]
  (map? thing))


#?(:clj
   (declare create-component)

   :cljs
   (defn create-component
     "Function that normalizes component definitions into a
  common signature, (fn [props & children]). This will typically not be called
  directly, use defcomponent instead."
     [component]
     (let [{:keys [store dispatch selectors actions other-meta]}
           (some-> component meta parse-component-meta)]
       (cond
         (and selectors (nil? store))
         (throw (new js/TypeError "No store provided for selectors"))

         (and actions (nil? dispatch))
         (throw (new js/TypeError "No dispatch provided for actions"))

         :else
         (let [component-wrapped
               (fn
                 ([]
                  (component nil))
                 ([props-or-child & children]
                  (if (is-props? props-or-child)
                    (apply component props-or-child children)
                    (apply component nil props-or-child children))))]
           (with-meta component-wrapped other-meta))))))

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
