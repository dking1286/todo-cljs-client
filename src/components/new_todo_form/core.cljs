(ns components.new-todo-form.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [state.core :refer [store dispatch]]))

(defn new-todo-form
  []
  [:div.new-todo-form
   [mui/paper
    [mui/toolbar
     [mui/toolbar-title "Create new todo"]]]
   [:div.fields-container
    [mui/text-field {:floating-label-text "Title"}]
    [mui/text-field {:floating-label-text "Body"}]
    [mui/raised-button "Create"]]])
