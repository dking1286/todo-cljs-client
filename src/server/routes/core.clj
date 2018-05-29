(ns server.routes.core
  (:require [ring.util.response :as r]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes root-handler
  (GET "/" []
    (-> (r/resource-response "index.html" {:root "public"})
        (r/content-type "text/html"))))