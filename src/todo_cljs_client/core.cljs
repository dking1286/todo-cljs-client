(ns todo-cljs-client.core
  (:require [goog.dom]
            [reagent.core :as reagent]
            [components.root.core :refer [root]]
            [todo-cljs-client.init :refer [on-init]]))

(enable-console-print!)

(on-init)

(reagent/render
 [root]
 (goog.dom/getRequiredElement "root"))
