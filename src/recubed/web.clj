(ns recubed.web
  (:gen-class)
  (:use compojure.core)
  (:use ring.adapter.jetty)
  (:require [compojure.route :as route]
            [ring.util.response :as resp]))

(defroutes app
  (GET "/" [] (resp/file-response "index.html" {:root "resources/public"}))
  (route/resources "/"))

(defn -main
  [& args]
  (run-jetty app {:port 3000}))
