(ns logical-interpreter
  (:require [parser :refer :all])
  (:require [clojure.string :refer :all])
)

(defn solve-query
  [database query]
  (def query-rule (second (re-matches #"([a-z]+)\(.+" query)))
  (def query-params (get-params query))
  (def rule-definition (get-rule-def database query-rule))

  (if (empty? rule-definition)
    false
    (if (not= (count (get-params rule-definition)) (count query-params))
      false
      (every? (fn [x] (fact-exists? database x))
        (get-rule-facts rule-definition (get-params rule-definition) query-params)
      )
    )
  )
)

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [text-database query]
  (def database (get-database text-database))
  (if (valid-entries? database query)
    (or (fact-exists? database query) (solve-query database query))
    nil
  )
)
