(ns logical-interpreter
  (:require [syntax-validator :refer :all])
  (:require [parser :refer :all])
  (:require [clojure.string :refer :all])
)


; (defn get-rules
;   [database]
;   (doall (filter valid-rule? database))
; )

; podria crear un mapa con X: param1 Y: param2 puedo usar contains? seguramente
(defn solve-query
  [database query]
  (def query-rule (second (re-matches #"([a-z]+)\(.+" query))) ;ver como agarrar el match que quiero
  (def query-params (get-params query))
  ; (def rules (get-rules database))
  ;TODO aclarar que una rule solo esta hechca de facts y no otras rules (lo dic el enunciado)
  ; (def rule-definition (first (filter (fn [x] (re-find (re-pattern query-rule) x)) (get-rules database))))
  ; (def rule-definition (doall (filter (fn [x] (re-find (re-pattern (str query-rule "\\(.+\\) :- ")) x)) (get-rules database))))
  (def rule-definition (get-rule-def database query-rule))
  ; (def rule-params (get-params rule-definition))

  (if (empty? rule-definition)
    false

      ; (def rule-params (get-params rule-definition))
    (if (not= (count (get-params rule-definition)) (count query-params))
      ; nil
      false
      ; en vez de true deberia hacer el calculo aca
      (every? (fn [x] (fact-exists? database x))
        (get-rule-facts rule-definition (get-params rule-definition) query-params)
      )
    )

  )

  ; (def rule-params (get-params (first rule-definition)))
  ;TODO para cada rule-params reemplazar en rule-definition con query-params
  ;se devuelve nil si la regla pasada no existe o si los parametros entre el query y la definicion no coinciden
  ; (if (not= (count rule-params) (count query-params))
  ;   ; nil
  ;   false
  ;   ; en vez de true deberia hacer el calculo aca
  ;   (every? (fn [x] (fact-exists? database x)) (get-rule-facts rule-definition rule-params query-params))
  ; )
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
