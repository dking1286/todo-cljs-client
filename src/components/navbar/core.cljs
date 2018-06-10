(ns components.navbar.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]))

(defui NavBar
  Object
  (render [this]
    (mui/app-bar {:title "Todo Voodoo"})))

(def navbar (om/factory NavBar))
