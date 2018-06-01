(ns resources.todos.core
  (:require [clojure.spec.alpha :as s]))

(s/def ::id integer?)
(s/def ::title string?)
(s/def ::body string?)
(s/def ::user-id integer?)
(s/def ::done? boolean?)
