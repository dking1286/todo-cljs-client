(ns todo-cljs-client.core
  (:require [cljs.user]  ;; Load user namespace for development
            [om.next :as om]
            [om.next.protocols :as protocols]
            [goog.dom :as gdom]
            [state.core :refer [state parser]]
            [components.root.core :refer [Root]]))

(enable-console-print!)

(def reconciler
  (om/reconciler {:state state
                  :parser parser}))

(om/add-root! reconciler Root (gdom/getElement "root"))

