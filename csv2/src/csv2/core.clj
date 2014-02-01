(ns csv2.core
  (:use [csv-map.core] 
        [clojure.java.jdbc] ))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"
   })

(defn create-db []
  (try (with-connection db 
         (create-table :supply
                       [:manager :text]
                       [:name :text]
                       [:id :text]
                       [:january :text]
                       [:february :text]))
       (catch Exception e (println e))))

(defn drop-db []
  (try (with-connection db 
         (drop-table :supply))
       (catch Exception e (println e))))

;;; Define key functions

; convert keys in map(dir) to map with keywords
(defn keyw [dir] 
  (into {} (map #(assoc {} (keyword (key %))(val %)) dir)))

;{:fizz "buzz", :city "winchester", :name "keith"}

;({"February" "1", "January" "1", "Id" "21598", "Name" "Bob", "Manager" "Mike"} {"February" "1", "January" "0", "Id" "12345", "Name" "Angie", "Manager" "Steve"})
;; csv2.core> (keys (first resource-map))
;;;("February" "January" "Id" "Name" "Manager")

(drop-db)
(create-db)

(comment
(def resource-map (parse-csv (slurp "file.csv")))
(def resources (map keyw resource-map))

(map #(with-connection db
  (insert-records :supply %)) resources)

(def output
  (with-connection db
    (with-query-results rs ["select * from supply"] (doall rs))))

(keys (first output))

)

(map #(with-connection db
  (insert-records :supply %)) allsupply2)
;(({:last_insert_rowid() 7}) ({:last_insert_rowid() 8}))
; adds records
