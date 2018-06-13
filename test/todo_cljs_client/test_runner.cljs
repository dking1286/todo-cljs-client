(ns todo-cljs-client.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            ;; Import all test namespaces here
            [state.queries-test]))

(enable-console-print!)

(doo-tests
  'state.queries-test)
