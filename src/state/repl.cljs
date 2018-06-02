(ns state.repl
  (:require [state.core :refer [store]]
            [state.middleware.logging :refer [action-history]]))
