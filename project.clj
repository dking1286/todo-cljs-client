(defproject todo-cljs-client "0.1.0-SNAPSHOT"
  :description "The client for the world's most overbuilt todo application"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
            
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [environ "1.1.0"]
                 [ring "1.6.3"]
                 [ring-logger "0.7.7"]
                 [compojure "1.6.0"]
                 [com.cognitect/transit-cljs "0.8.256"]]
                 
  :plugins [[lein-environ "1.1.0"]
            [lein-ring "0.12.3"]
            [lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.16"]
            [lein-sass "0.5.0"]
            [lein-shell "0.5.0"]]
            
  :source-paths ["src"]
  :test-paths ["test"]
  :target-path "target/%s"
  :clean-targets ^{:protect false} [:target-path]
  :repl-options {:port 3006}
  :uberjar-name "todo-cljs-client-standalone.jar"
  
  :ring
  {:port 3005
   :init server.core/on-init
   :handler server.core/app}
  
  :figwheel
  {:server-port 9090
   :css-dirs ["resources/public/css"]}

  :sass
  {:command :sass
   :src "resources/sass"
   :output-directory "resources/public/css/compiled"}

  :cljsbuild
  {:builds {:app {:source-paths ["src"]
                  :compiler {:main todo-cljs-client.core
                             :asset-path "js/compiled/out"
                             :output-to "resources/public/js/compiled/main.js"
                             :output-dir "resources/public/js/compiled/out"
                             :language-in :ecmascript6
                             :language-out :ecmascript5}}}}
  
  :profiles
  {:dev {:env {:environment "development"
         :dependencies [[cljfmt "0.5.7"]]}
         :cljsbuild {:builds {:app {:figwheel true
                                    :compiler {:optimizations :none
                                               :source-map true
                                               :preloads [process.env
                                                          cljs.user]}}}}}
         
   :test {:env {:environment "test"}
          :dependencies [[pjstadig/humane-test-output "0.8.3"]]
          :plugins [[com.jakemccrary/lein-test-refresh "0.12.0"]]
          :injections [(require 'pjstadig.humane-test-output)
                       (pjstadig.humane-test-output/activate!)]
                        :test-refresh {:quiet true
                        :changes-only true
                        :watch-dirs ["src" "test"]}}

   :prod {:env {:environment "production"}
          :cljsbuild {:builds {:app {:compiler {:optimizations :advanced}}}}}}
   
  :aliases
  {"figwheel:dev" ["do" "clean"
                        ["with-profile" "+dev,+local-dev" "figwheel"]]
   "server:dev" ["with-profile" "+dev,+local-dev" "ring" "server-headless"]
   "build-client:prod" ["do" "clean"
                             ["with-profile" "prod" "cljsbuild" "once"]
                             ["shell" "rm" "-rf" "./resources/public/js/compiled/out"]]
   "build-server:prod" ["do" "clean"
                             ["with-profile" "prod" "ring" "uberjar"]]})