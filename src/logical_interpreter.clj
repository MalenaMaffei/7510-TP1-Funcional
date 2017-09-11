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
  ; TODO usar forma clujure, esto es java
  (.contains database fact)
)

(defn rule-exists?
  [database rule]
)

(defn get-rules
  [database]
  (filter valid-rule? database)
)


; podria crear un mapa con X: param1 Y: param2 puedo usar contains? seguramente
(defn solve-query
  [database query]
  (def query-rule (get (re-matches #"([a-z]+)\(.+" query) 1))
  (def rules (get-rules database))
  (def rule-definition (filter (fn [x] (re-matches (re-pattern (str query-rule "\\(.+")) x)) rules))
  (if (empty? rule-definition)
    false
    ; en vez de true deberia hacer el calculo aca
    true

  )
)


(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [text-database query]
  (def database (get-database text-database))
  (if (and (valid-database? database) (valid-fact? query))
    (or (fact-exists? database query) (solve-query database query))
    nil)
)
