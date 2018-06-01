(ns components.top-nav-bar.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [components.core :refer-macros [defcomponent]]))

(defcomponent top-nav-bar
  [props & children]
  [:div "Hello"])
