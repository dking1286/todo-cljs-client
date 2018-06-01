(ns components.root.core
  (:require [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as mui]
            [components.todo-list.core :refer [todo-list]]
            [components.new-todo-form.core :refer [new-todo-form]]
            [components.top-nav-bar.core :refer [top-nav-bar]]))

(def theme
  (get-mui-theme
   {:palette {:text-color (color :blue200)}}))

(defn root
  "Root view component of the todo list application"
  []
  [mui/mui-theme-provider {:mui-theme theme}
   [:div.root
    [top-nav-bar]
    [todo-list]
    [new-todo-form]]])
