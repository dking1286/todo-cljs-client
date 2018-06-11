(ns resources.todos.mutations
  (:require [clojure.spec.alpha :as s]
            [state.queries :refer [mutate]]
            [resources.todos.spec]
            [utils.id-generation :refer [generate-id]]))

(defmethod mutate 'todos/add
  [{:keys [state]} _ params]
  ;{:pre [(s/valid? :todos/todo-creation-data params)]}
  ; FIXME: Make the spec work correctly
  ; FIXME: Have the id be generated on the server side
  (let [id (generate-id)
        todo (merge params {:id id :user-id 1 :done? false})]
    {:value {:keys [:todos/by-id :todos/list]}
     :action
     #(swap! state (fn [st]
                     (-> st
                         (assoc-in [:todos/by-id id] todo)
                         (update :todos/list conj [:todos/by-id id]))))}))

(defmethod mutate 'todos/select
  [{:keys [state]} _ {:keys [id]}]
  {:value {:keys [:todos/selected]}
   :action
   #(swap! state (fn [st]
                   (assoc st :todos/selected [:todos/by-id id])))})

(defmethod mutate 'todos/change-new-todo-form
  [{:keys [state]} _ params]
  {:value {:keys [:todos/new-todo-form]}
   :action
   #(swap! state (fn [st]
                   (update st :todos/new-todo-form merge params)))})
