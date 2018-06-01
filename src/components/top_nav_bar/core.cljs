(ns components.top-nav-bar.core
  (:require [cljs-react-material-ui.reagent :as mui]
            [components.core :refer-macros [defcomponent]]))

(defcomponent top-nav-bar
  "View component representing the nav bar at the top of the screen."
  {:component-did-mount (fn [this] (println "Navbar mounted."))}
  [props & children]
  [:div "Hello"])
