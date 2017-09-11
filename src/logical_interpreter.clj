(ns logical-interpreter
  (:require [validator :refer :all])
  (:require [clojure.string :refer :all])
)

(defn get-database
  [text-database]
  (map trim
          (doall (filter (fn [x] (not (blank? x))) (split text-database #"\.")))
  )
)

(defn fact-exists?
  [database fact]
  (.contains database fact)
)

(defn rule-exists?
  [database rule]
)


(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [text-database query]
  (def database (get-database text-database))
  (if (and (valid-database? database) (valid-fact? query))
    (or (fact-exists? database query) (solve-rule ))
    nil)
)
