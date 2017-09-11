(ns logical-interpreter
  (:require [parser :refer :all])
)

(defn solve-query
  [database query]
  ; gets the name of the rule
  (def query-rule (second (re-matches #"([a-z]+)\(.+" query)))

  ; gets the params inside the query rule
  (def query-params (get-params query))

  ; pulls the rule's definition from the databse
  (def rule-definition (get-rule-def database query-rule))

  (if (empty? rule-definition)
  ; if empty, the rule did not exist, so query is false
    false
    (if (not= (count (get-params rule-definition)) (count query-params))
    ; if definition and query parameters don't match, result is nil
      nil
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
