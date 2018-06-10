(ns resources.todos.spec
  (:require [clojure.spec.alpha :as s]))

(s/def :todos/id integer?)
(s/def :todos/title (s/and string? seq))
(s/def :todos/body (s/and string? seq))
(s/def :todos/user-id integer?)
(s/def :todos/done? boolean?)

(s/def :todos/todo (s/keys :req [:todos/id
                                 :todos/title
                                 :todos/body
                                 :todos/user-id
                                 :todos/done?]))

(s/def :todos/todo-creation-data (s/keys :req [:todos/title
                                               :todos/body
                                               :todos/user-id]))
