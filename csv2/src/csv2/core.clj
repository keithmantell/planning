(ns csv2.core
  (:use [csv-map.core]) 
  (:use [clojure.java.jdbc :as jdbc] )
  (:require [clojure.java.io :as io]))


;; read files

;;; read files from current loaction ( and sub-directories))
(defn get-file-names []
  (->> (file-seq (io/file "."))
       (filter #(.isFile %))
       (map #(.getPath %))
       (filterv #(.endsWith % ".csv"))))
;user> (get-file-names)
; => ["./file.csv" "./src/csv2/file.csv"]

(def data
  (doall (map #(parse-csv (slurp % ) :key :keyword :delimiter \;)(get-file-names))))
;(({:feb14 "1", :jan14 "1", :id "21598", :resource "Bob", :manager "Mike"}))

(keys (first data))
; (:role :category :nov14 :may14 :mar14 :jan14 :sep14 :aug14 :jun14 :type :source :dec14 :jul14 :oct14 :manager :project :id :resource :apr14 :dec13 :location :feb14)

;; define database connection details
(def db-spec
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"
   })

(defn demand-table []
  (jdbc/create-table-ddl :demand
                         [:project :text]
                         [:category :text]
                         [:january :text][:february :text][:march :text][:april :text][:may :text][:june :text][:july :text][:august :text][:september :text][:october :text][:november :text][:december :text]))
(defn supply-table []
  (jdbc/create-table-ddl :supply
                         [:name :text]
                         [:id :text]
                         [:category :text]
                         [:project :text]
                         [:january :text][:february :text][:march :text][:april :text][:may :text][:june :text][:july :text][:august :text][:september :text][:october :text][:november :text][:december :text]))
(defn insert-supply-row [row]
  (jdbc/insert! db-spec :supply row))

(defn insert-demand-row [row]
  (jdbc/insert! db-spec :demand row))

;; Resource categories (set)
(def categories #{:pm :rm :jit :vm :svt :perf})
;; Resource projects
(def projects [:java9 ["Delivery" "TechDev" "Platforms" "Performance"] :java8 ["Delivery" "TechDev"]])


;(drop-db)
;(create-db)

(comment
  (def resource-map (parse-csv (slurp "file.csv")))
  (def resources (map keyw resource-map))
  
  (map #(with-connection db
          (insert-records :supply %)) resources)
  
  (def output
    (with-connection db
      (with-query-results rs ["select * from supply"] (doall rs))))
  
  (keys (first output))
  
  
  
  (map #(with-db-connection db
          (insert-records :supply %)) allsupply2)

; create table
(jdbc/db-do-commands db-spec (demand-table))
; insert a line
(jdbc/insert! db-spec :demand {:project "proj1"})
;update project
(jdbc/update! db-spec :demand {:category "Test" :january "1"} ["project = ?" "proj1"])
                                        ;(({:last_insert_rowid() 7}) ({:last_insert_rowid() 8}))
                                        ; adds records
  )

