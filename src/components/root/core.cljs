(ns components.root.core
  (:require [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as mui]
            [components.core :refer-macros [defcomponent]]
            [components.top-nav-bar.core :refer [top-nav-bar]]
            [components.todos-view.core :refer [todos-view]]))

(defn root
  "Root view component of the todo list application."
  [props & children]
  [mui/mui-theme-provider
   [:div.root
    [top-nav-bar]
    [todos-view]]])
