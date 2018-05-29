(ns server.core-test
  (:require [clojure.test :refer :all]
            [server.core :refer [on-init app]]))

(deftest on-init-test
  (testing "on-init"
    (testing "should println the current environment"
      (let [output (with-out-str (on-init))]
        (is (re-find #"Using environment test" output))))))