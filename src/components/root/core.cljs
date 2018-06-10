(ns components.root.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]
            [components.navbar.core :refer [navbar]]))

(defui Root
  static om/IQuery
  (query [this]
    [:greeting])

  Object
  (render [this]
    (let [{:keys [greeting]} (om/props this)]
      (mui/mui-theme-provider
       (dom/div
        #js {:className "Root"}
        (navbar)
        (dom/div nil greeting))))))

(def root (om/factory Root))
