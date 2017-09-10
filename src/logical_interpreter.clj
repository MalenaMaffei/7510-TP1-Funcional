(ns logical-interpreter
  (:require [validator :refer :all]))

"(split-lines s)"
(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database query]
  (if (and (valid-database? database) (valid-fact? query))
    true
    nil)
)
