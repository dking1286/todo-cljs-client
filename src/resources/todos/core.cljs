(ns resources.todos.core
  (:require [clojure.spec.alpha :as s]
            [resources.todos.reducer]))

(s/def ::id integer?)
(s/def ::title string?)
(s/def ::body string?)
(s/def ::user-id integer?)
(s/def ::done? boolean?)
