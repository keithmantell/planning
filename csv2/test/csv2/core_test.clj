(ns csv2.core-test
  (:use midje.sweet)
  (:use [csv2.core :as core])
  (:use [clojure.java.jdbc :as jdbc]))

(println "You sbould expect to see three failures below.")

(facts "db-connection"
  (fact "stuff"
        (jdbc/with-db-connection [db-con core/db-spec] 
          (jdbc/create-table-ddl :supply
                        [:manager :text]
                        [:name :text]
                        [:id :text]
                        [:january :text]
                        [:february :text])) => true))
