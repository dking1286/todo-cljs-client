(ns server.core
  (:require [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [server.routes.core :refer [root-handler]]
            [environ.core :refer [env]]))

(defn on-init
  []
  (println (str "Using environment " (env :environment))))

(def app
  (-> root-handler
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))