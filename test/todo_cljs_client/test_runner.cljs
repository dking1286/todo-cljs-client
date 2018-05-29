(ns todo-cljs-client.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            ;; Import all test namespaces here
            [todo-cljs-client.init-test]))

(enable-console-print!)

(doo-tests
  'todo-cljs-client.init-test)