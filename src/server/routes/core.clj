(ns server.routes.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes root-handler
  (GET "/" []
    "Running"))