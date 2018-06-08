(ns components.root.core
  (:require [om.next :as om :refer [defui]]
            [om.dom :as dom]))

(defui Root
  static om/IQuery
  (query [this]
    [:greeting])

  Object
  (render [this]
    (let [{:keys [greeting]} (om/props this)]
      (dom/div nil greeting))))

(def root (om/factory Root))
