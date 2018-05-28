(ns server.core
  (:require [server.routes.core :refer [root-handler]]
            [environ.core :refer [env]]))

(defn on-init
  []
  (println (str "Using environment " (env :environment))))

(def app root-handler)