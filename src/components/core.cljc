(ns components.core
  #?(:cljs
     (:require [reagent.core :as reagent]
               [utils.funcs :refer [comp-preserve-meta]])))

;; TODO: FIgure out how to attach the docstring as metadata on
;; the var

(defn- create-dispatcher
  [dispatch action]
  (fn [& args] (dispatch (apply action args))))

(defn- get-state-props
  [store selectors]
  (->> selectors
       (map (fn [[key selector]] [key (selector @store)]))
       (into {})))

(defn- get-action-props
  [dispatch actions]
  (->> actions
       (map (fn [[key action]] [key (create-dispatcher dispatch action)]))
       (into {})))

#?(:cljs
   (defn- wrap-selected-props
     [component]
     (let [{:keys [store dispatch selectors actions]} (meta component)]
       (when (and selectors (not store))
         (throw (js/TypeError. "No store provided with selectors")))
       (when (and actions (not dispatch))
         (throw (js/TypeError. "No dispatch function provided with actions")))
       (fn [props & args]
         (let [state-props (get-state-props store selectors)
               action-props (get-action-props dispatch actions)]
           (apply component (merge state-props action-props props) args))))))

#?(:cljs
   (defn- wrap-stateful
     [component]
     (let [{:keys [initial-state]} (meta component)]
       (if initial-state
         (fn [props & args]
           (let [state (reagent/atom initial-state)]
             (fn [props & args]
               (apply component props state args))))
         component))))

#?(:clj
   (declare create-component)

   :cljs
   (defn create-component
     [component-defn]
     (let [transform (comp-preserve-meta wrap-stateful wrap-selected-props)]
       (transform component-defn))))

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
