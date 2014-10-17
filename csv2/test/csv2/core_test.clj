(ns csv2.core-test
  (:use [midje.sweet :as midje])
  (:use [csv2.core :as core])
  (:use [clojure.java.jdbc :as jdbc]))

(midje/facts "db-connection"
             
  (midje/fact "stuff"
        (jdbc/db-do-commands core/db-spec
                             (jdbc/drop-table-ddl :supply :entities clojure.string/upper-case)
                             core/supply-table
                            )
 


