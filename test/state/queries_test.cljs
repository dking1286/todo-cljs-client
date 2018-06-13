(ns state.queries-test
  (:require [clojure.test :refer-macros [deftest is testing]]
            [resources.core]
            [state.queries :refer [read]]))

(deftest read-todos-list-test
  (testing "read :todos/list"
    (testing "should return a list of todos"
      (let [state (atom {:todos/by-id {1 {:title "Hello"
                                          :body "world"}
                                       2 {:title "Goodbye"
                                          :body "world"}}
                         :todos/list [[:todos/by-id 1]
                                      [:todos/by-id 2]]})
            expected [{:title "Hello" :body "world"}
                      {:title "Goodbye" :body "world"}]]
        (is (= (read {:state state} :todos/list nil))
            expected)))))
