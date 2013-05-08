(defproject recubed "0.1.0-SNAPSHOT"
  :description "Rubik's Cube in ClojureScript."
  :url "http://github.com/rboyd/recubed"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "0.3.0"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-jetty-adapter "1.2.0-beta2"]
                 [compojure "1.1.5"]]
  :main recubed.web
  :cljsbuild {
              :builds [{
                        :source-paths ["src-cljs"]
                          :compiler {
                                   :output-to "resources/public/recubed.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
