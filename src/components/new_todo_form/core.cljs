(ns components.new-todo-form.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [state.initial :refer [initial-state]]
            [cljs-react-material-ui.core :as mui]))

(defn- validation-errors
  [{:keys [title title-dirty? body body-dirty?]}]
  (cond-> {}
    (and title-dirty? (empty? title))
    (assoc :title-error "Title is required")
    (and body-dirty? (empty? body))
    (assoc :body-error "Body is required")))

(def all-dirty {:title-dirty? true :body-dirty? true})

(defui NewTodoForm
  static om/IQuery
  (query [this]
    '[:title :title-error :title-dirty?
      :body :body-error :body-dirty?
      :expanded?])

  Object
  (on-click-open [this e]
    (om/transact!
     this `[(todos/change-new-todo-form {:expanded? true})
            :todos/new-todo-form]))

  (on-submit [this e] ; TODO: add validation so you can't submit an empty todo
    (.preventDefault e)
    (let [{:keys [title body]} (om/props this)
          todo {:title title :body body}
          errors (validation-errors (om/props this))]
      (if-not (empty? errors)
        (om/transact!
         this `[(todos/change-new-todo-form ~(merge errors all-dirty))
                :todos/new-todo-form])
        (om/transact!
         this `[(todos/add ~todo)
                (todos/change-new-todo-form ~(initial-state :todos/new-todo-form))
                :todos/list
                :todos/by-id
                :todos/new-todo-form]))))

  (render [this]
    (let [{:keys [title title-error title-dirty 
                  body body-error body-dirty
                  expanded?]}
          (om/props this)]
      (if-not expanded?
        (mui/raised-button
         {:style {:width "100%"
                  :color "white"}
          :primary true
          :on-click #(.on-click-open this %)}
         "Create new todo")
        (mui/card
         (mui/card-title {:title "New todo"})
         (mui/card-text
          (dom/form
           #js {:onSubmit #(.on-submit this %)}
           (mui/text-field
            {:name "title"
             :floating-label-text "Title"
             :error-text (when title-error
                           (dom/span #js {} title-error))
             :default-value title
             :on-change (fn [e]
                          (let [new-state {:title (.. e -target -value)}]
                            (om/transact!
                             this `[(todos/change-new-todo-form ~new-state)
                                    :todos/new-todo-form])))
             :on-blur (fn [e]
                        (om/transact!
                         this `[(todos/change-new-todo-form {:title-dirty? true})
                                :todos/new-todo-form]))})
           (mui/text-field
            {:name "body"
             :floating-label-text "Body"
             :error-text (when body-error
                           (dom/span #js {} body-error))
             :default-value body
             :full-width true
             :multi-line true
             :on-change (fn [e]
                          (let [new-state {:body (.. e -target -value)}]
                            (om/transact!
                             this `[(todos/change-new-todo-form ~new-state)
                                    :todos/new-todo-form])))
             :on-blur (fn [e]
                        (om/transact!
                         this `[(todos/change-new-todo-form {:body-dirty? true})
                                :todos/new-todo-form]))})
           (mui/raised-button
            {:type "submit"
             :primary true
             :style {:color "white"}}
            "Submit"))))))))

(def new-todo-form (om/factory NewTodoForm))
