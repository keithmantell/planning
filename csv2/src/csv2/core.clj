(ns csv2.core
  (:use [csv-map.core]) 
  (:use [clojure.java.jdbc :as jdbc] ))

;; write supply csv files to database supply table

;; define database connection details
(def db-spec
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"
   })

(defn supply-table []
  (jdbc/create-table-ddl :supply
                         [:manager :text]
                         [:name :text]
                         [:id :text]
                         [:category :text]
                         [:january :text]
                         [:february :text]))
(defn insert-row [row]
  (jdbc/insert! db-spec :supply row))

;; Resource categories (set)
(def categories #{pm rm jit vm svt perf})
;; Resource projects
(def projects [:java9 ["Delivery" "TechDev" "Platforms" "Performance"] :java8 ["Delivery" "TechDev"]])


;;; Define key functions

; convert keys in map(dir) to map with keywords
(defn keyw [dir] 
  (into {} (map #(assoc {} (keyword (key %))(val %)) dir)))

;{:fizz "buzz", :city "winchester", :name "keith"}

;({"February" "1", "January" "1", "Id" "21598", "Name" "Bob", "Manager" "Mike"} {"February" "1", "January" "0", "Id" "12345", "Name" "Angie", "Manager" "Steve"})
;; csv2.core> (keys (first resource-map))
;;;("February" "January" "Id" "Name" "Manager")

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
                                        ;(({:last_insert_rowid() 7}) ({:last_insert_rowid() 8}))
                                        ; adds records
  )
