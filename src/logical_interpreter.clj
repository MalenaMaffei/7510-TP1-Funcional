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
  (doall (filter valid-rule? database))
)

(defn get-params
  [query]
  (split (second (re-matches #"^[a-z]+\(([^\)]+)\).*$" query)) #", ")
)

(defn fill-rule
  [rule-definition rule-params query-params]
  (def to-replace (join "|" rule-params))
  (def replacement-map (zipmap rule-params query-params))
  (clojure.string/replace rule-definition (re-pattern to-replace) replacement-map)
)



; podria crear un mapa con X: param1 Y: param2 puedo usar contains? seguramente
(defn solve-query
  [database query]
  (def query-rule (second (re-matches #"([a-z]+)\(.+" query))) ;ver como agarrar el match que quiero
  (def query-params (get-params query))
  ; (def rules (get-rules database))
  ;TODO aclarar que una rule solo esta hechca de facts y no otras rules (lo dic el enunciado)
  (def rule-definition (first (filter (fn [x] (re-find (re-pattern query-rule) x)) (get-rules database))))
  ; (def rule-params (get-params rule-definition))
  (def rule-params (get-params rule-definition))
  ;TODO para cada rule-params reemplazar en rule-definition con query-params
  ;se devuelve nil si la regla pasada no existe o si los parametros entre el query y la definicion no coinciden
  (if (or (empty? rule-definition) (not= (count rule-params) (count query-params)))
    nil
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
