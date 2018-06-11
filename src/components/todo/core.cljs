(ns components.todo.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as mui]))

(defui Todo
  static om/Ident
  (ident [this {:keys [id]}]
    [:todos/by-id id])

  static om/IQuery
  (query [this]
    '[:id :title :body :done?])

  Object
  (render [this]
    (let [{:keys [title body]} (om/props this)]
      (dom/div
       #js {:className "Todo"}
       (mui/card
        (mui/card-title {:title title})
        (mui/card-text body))))))

(def todo
  (om/factory Todo {:keyfn :id}))
