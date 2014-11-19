(ns seat-picker.core
  (:require [ring.adapter.jetty :refer :all]
            [compojure.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route])
  (:gen-class))



(defroutes handler
  (GET "/" [] "<h1>Hello World</h1>")
  (route/not-found "<h1>Page not found</h1>"))


(defn -main
  "Main function"
  [& args])
