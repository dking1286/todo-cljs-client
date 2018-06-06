(ns components.new-todo-form.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [state.core :refer [store dispatch]]
            [resources.todos.actions :as todo-actions]
            [components.core :refer-macros [defcomponent]]
            [components.primary-button.core :refer [primary-button]]))

(defcomponent new-todo-form
  {:dispatch dispatch
   :actions {:add-todo todo-actions/add}
   :initial-state {:title nil :body nil :expanded? false}}
  [{:keys [add-todo]} state & children]
  [:div.new-todo-form
   (when-not (@state :expanded?)
     [primary-button {:class-name "new-todo-button"
                      :full-width true
                      :on-click #(swap! state update :expanded? not)}
      "Add new todo"])
   (when (@state :expanded?)
     [mui/paper
      [:form.new-todo-form-fields
       {:on-submit (fn [e]
                     (.preventDefault e)
                     (swap! state update :expanded? not)
                     (-> @state
                         (select-keys [:title :body])
                         add-todo))} ;; FIXME: Need to add dispatch behavior in defcomponent
       [mui/text-field {:floating-label-text "Title"
                        :on-change (fn [e]
                                     (let [title (-> e .-target .-value)]
                                       (swap! state assoc :title title)))}]
       [mui/text-field {:floating-label-text "Type something..."
                        :multi-line true
                        :full-width true
                        :on-change (fn [e]
                                     (let [body (-> e .-target .-value)]
                                       (swap! state assoc :body body)))}]
       [primary-button {:style {:width "200px"}
                        :type "submit"} 
        "Submit"]]])])
