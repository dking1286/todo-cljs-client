(ns utils.react
  #?(:cljs
     (:require-macros [utils.react :refer [handler-fn]])))

#?(:clj
   (defmacro handler-fn
     [& body]
     `(fn [~'event] ~@body nil)))
