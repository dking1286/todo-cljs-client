(ns todo-cljs-client.init-test
  (:require [clojure.test :refer [deftest testing is]]
            [todo-cljs-client.init :refer [on-init]]))

(deftest on-init-test
  (testing "on-init"
    (testing "should print out a message to the console"
      (is (re-find #"Todo cljs initialized" (with-out-str (on-init)))))))