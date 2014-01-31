(ns csv2.core
  (:use [csv-map.core] 
        [clojure.java.jdbc] ))

(def resource-map (parse-csv (slurp "file.csv")))

;({"February" "1", "January" "1", "Id" "21598", "Name" "Bob", "Manager" "Mike"} {"February" "1", "January" "0", "Id" "12345", "Name" "Angie", "Manager" "Steve"})
;; csv2.core> (keys (first resource-map))
;;;("February" "January" "Id" "Name" "Manager")

;(map (keyword first resource-map))

(def entry (first resource-map))

(map (fn [key value] (db-add (keyword (key %)) (val %))) entry)


(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"
   })

; convert keys in map to map with keywords
(defn keyw [dir] 
  (map #(assoc {} (keyword (key %)) (val %)) dir))

(defn create-db []
  (try (with-connection db 
         (create-table :supply
                       [:manager :text]
                       [:name :text]
                       [:id :text]
                       [:january :text]
                       [:february :text]))
       (catch Exception e (println e))))

(create-db)

(with-connection db
  (insert-records :supply resource-map))

(def output
  (with-connection db
    (with-query-results rs ["select * from supply"] (doall rs))))

(keys (first output))
