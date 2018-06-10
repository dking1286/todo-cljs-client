(ns todo-cljs-client.core
  (:require [cljsjs.react] ;; Load react globally
            [cljsjs.material-ui] ;; Load material ui globally
            [cljs.user] ;; Load user namespace for development
            [resources.core] ;; Initialize resource
            [om.next :as om]
            [goog.dom :as gdom]
            [state.core :refer [state parser]]
            [components.root.core :refer [Root]]))

(enable-console-print!)

(def reconciler
  (om/reconciler {:state state
                  :parser parser}))

(om/add-root! reconciler Root (gdom/getElement "root"))

