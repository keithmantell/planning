(defproject csv2 "0.0.1-SNAPSHOT"
  :description "Cool new project to do things and stuff"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [csv-map "0.1.0-SNAPSHOT"]
                 [org.clojure/java.jdbc "0.0.6"]
                 [org.xerial/sqlite-jdbc "3.7.2"]]
  :profiles {:dev {:dependencies [[midje "1.6.0"]]}})
  
