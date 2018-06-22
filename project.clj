(defproject dking1286/todo-cljs-client "0.1.0-SNAPSHOT"
  :description "The client for the world's most overbuilt todo application"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async "0.4.474"]
                 [environ "1.1.0"]
                 [ring "1.6.3"]
                 [ring-logger "1.0.1"]
                 [compojure "1.6.0"]
                 [com.cognitect/transit-cljs "0.8.256"]
                 [org.omcljs/om "1.0.0-beta4"]
                 [cljs-react-material-ui "0.2.48"]
                 [figwheel-sidecar "0.5.16"]]

  :plugins [[lein-environ "1.1.0"]
            [lein-ring "0.12.3"]
            [lein-cljsbuild "1.1.7"]
            [lein-sass "0.5.0"]
            [lein-shell "0.5.0"]]

  :source-paths ["src"]
  :test-paths ["test"]
  :target-path "target/%s"
  :clean-targets ^{:protect false} [:target-path]
  :uberjar-name "todo-cljs-client-standalone.jar"

  :repl-options
  {:port 3006}

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
  {:builds [{:id "app-dev"
             :source-paths ["src"]
             :figwheel true
             :compiler {:main todo-cljs-client.core
                        :asset-path "js/compiled/dev-out"
                        :output-to "resources/public/js/compiled/main.js"
                        :output-dir "resources/public/js/compiled/dev-out"
                        :language-in :ecmascript6
                        :language-out :ecmascript5
                        :optimizations :none
                        :source-map true
                        :preloads [cljs.user]}}
            {:id "app-prod"
             :source-paths ["src"]
             :compiler {:main todo-cljs-client.core
                        :output-to "resources/public/js/compiled/main.js"
                        :output-dir "resources/public/js/compiled/prod-out"
                        :language-in :ecmascript6
                        :language-out :ecmascript5
                        :optimizations :advanced
                        :source-map "resources/public/js/compiled/main.map.js"}}
            {:id "node-tests"
             :source-paths ["src" "test"]
             :compiler {:main todo-cljs-client.test-runner
                        :target :nodejs
                        :asset-path "js/compiled/tests-out"
                        :output-dir "resources/public/js/compiled/tests-out"
                        :output-to "resources/public/js/compiled/tests-main.js"
                        :optimizations :simple}}]}

  :profiles
  {:dev {:env {:environment "development"}
         :dependencies [[cljfmt "0.5.7"]
                        [cider/piggieback "0.3.5"]
                        [org.clojure/tools.nrepl "0.2.13"]]
         :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}

   :test {:env {:environment "test"}
          :dependencies [[pjstadig/humane-test-output "0.8.3"]]
          :plugins [[com.jakemccrary/lein-test-refresh "0.12.0"]
                    [lein-doo "0.1.10"]]
          :injections [(require 'pjstadig.humane-test-output)
                       (pjstadig.humane-test-output/activate!)]
          :test-refresh {:quiet true
                         :changes-only true
                         :watch-dirs ["src" "test"]}}

   :prod {:env {:environment "production"}}}

  :aliases
  {"clean-server" ["shell" "rm" "-rf" "target"]
   "clean-client" ["do"
                   ["shell" "echo" "'Cleaning compiled client files'"]
                   ["shell" "rm" "-f" "resources/public/js/compiled/main.js"]
                   ["shell" "rm" "-rf" "resources/public/js/compiled/dev-out"]
                   ["shell" "rm" "-rf" "resources/public/js/compiled/prod-out"]]
   "clean-tests" ["do"
                  ["shell" "rm" "-f" "resources/public/js/compiled/tests-main.js"]
                  ["shell" "rm" "-rf" "resources/public/js/compiled/tests-out"]]
   "clean-all-outputs" ["do"
                        ["shell" "echo" "'Cleaning all outputs'"]
                        ["shell" "rm" "-rf" "resources/public/js/compiled/dev-out"]
                        ["shell" "rm" "-rf" "resources/public/js/compiled/prod-out"]
                        ["shell" "rm" "-rf" "resources/public/js/compiled/tests-out"]]
   "repl:dev" ["do"
               "clean-client"
               ["with-profile" "+dev,+local-dev" "repl"]]
   "server:dev" ["with-profile" "+dev,+local-dev" "ring" "server-headless"]
   "build-styles:dev" ["shell" "sass" "--watch" "resources/sass/index.scss:resources/public/css/index.css"]
   "test-server:watch" ["with-profile" "+test,+local-test" "test-refresh"]
   "test-server:once" ["with-profile" "+test,+local-test" "test" ":all"]
   "test-client:watch" ["do"
                        "clean-tests"
                        ["with-profile" "+test,+local-test" "doo" "node" "node-tests" "auto"]]
   "test-client:once" ["do"
                       "clean-tests"
                       ["with-profile" "+test,+local-test" "doo" "node" "node-tests" "once"]]
   "build-server:prod" ["do"
                        "clean-server"
                        ["with-profile" "prod" "ring" "uberjar"]]
   "build-client:prod" ["do"
                        "clean-client"
                        ["with-profile" "prod" "cljsbuild" "once" "app-prod"]]})
