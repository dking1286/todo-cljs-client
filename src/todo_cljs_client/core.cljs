(ns todo-cljs-client.core
  (:require [cljsjs.material-ui] ;; Load MaterialUI globally
            [goog.dom]
            [reagent.core :as reagent]
            [resources.core] ;; Initialize resources
            [components.root.core :refer [root]]
            [todo-cljs-client.init :refer [on-init]]))

(enable-console-print!)

(on-init)

(reagent/render
 [root]
 (goog.dom/getRequiredElement "root"))
