(ns components.root.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]
            [components.navbar.core :refer [navbar]]
            [components.todos-view.core :refer [todos-view TodosView]]))

(defui Root
  static om/IQuery
  (query [this]
    (let [todos-view-query (om/get-query TodosView)]
      `[~@todos-view-query]))

  Object
  (render [this]
    (let [{:keys [greeting] :as props} (om/props this)]
      (mui/mui-theme-provider
       (dom/div
        #js {:className "Root"}
        (navbar)
        (todos-view props))))))

(def root (om/factory Root))
