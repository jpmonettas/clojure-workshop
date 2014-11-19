(defproject workshop "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring "1.3.1"]
                 [compojure "1.2.1"]
                 [midje "1.6.3"]
                 [org.clojure/math.combinatorics "0.0.8"]
                 [org.clojure/data.json "0.2.4"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler seat-picker.core/handler
         :nrepl {:start? true}}
  :main ^:skip-aot seat-picker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

