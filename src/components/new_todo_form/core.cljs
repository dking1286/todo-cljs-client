(ns components.new-todo-form.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]))

(defui NewTodoForm
  static om/IQuery
  (query [this]
    '[:title :body :expanded?])

  Object
  (on-click-open [this e]
    (om/transact!
     this `[(todos/change-new-todo-form {:expanded? true})
            :todos/new-todo-form]))

  (on-submit [this e] ; TODO: add validation so you can't submit an empty todo
    (.preventDefault e)
    (let [{:keys [title body]} (om/props this)
          todo {:title title :body body}]
      (om/transact!
       this `[(todos/add ~todo)
              (todos/change-new-todo-form {:expanded? false :title nil :body nil})
              :todos/list
              :todos/by-id
              :todos/new-todo-form])))

  (render [this]
    (let [{:keys [title body expanded?]} (om/props this)]
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
             :default-value title
             :on-change (fn [e]
                          (let [new-state {:title (.. e -target -value)}]
                            (om/transact!
                             this `[(todos/change-new-todo-form ~new-state)
                                    :todos/new-todo-form])))})
           (mui/text-field
            {:name "body"
             :floating-label-text "Body"
             :default-value body
             :full-width true
             :multi-line true
             :on-change (fn [e]
                          (let [new-state {:body (.. e -target -value)}]
                            (om/transact!
                             this `[(todos/change-new-todo-form ~new-state)
                                    :todos/new-todo-form])))})
           (mui/raised-button
            {:type "submit"
             :primary true
             :style {:color "white"}}
            "Submit"))))))))

(def new-todo-form (om/factory NewTodoForm))
