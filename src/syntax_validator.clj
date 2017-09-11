(ns syntax-validator
    (:require [clojure.string :refer :all])
)

(def fact-syntax "[a-z\\-]+\\([a-z\\-]+(, [a-z\\-]+){0,}\\)")

(def rule-syntax (str "[a-z\\-]+\\([A-Z](, [A-Z]+){0,}\\)" " :\\- " "[a-z\\-]+\\([A-Z](, [A-Z]+){0,}\\)" "(, " "[a-z\\-]+\\([A-Z](, [A-Z]+){0,}\\)" "){0,}"))

(defn gen-pattern
    "generates regex pattern"
    [pattern]
    (re-pattern (str "^" pattern "$"))
)

;"TODO: rules tienen que ser validadas un poco mas, que las variables coincidan por lo menos

(defn valid-fact?
    "checks if input has valid fact format"
    [fact]
    (if (re-find (gen-pattern fact-syntax) fact)
        true
        false
    )
)

(defn valid-rule?
    "checks if input has valid rule format"
    [rule]
    (if (re-find (gen-pattern rule-syntax) rule)
        true
        false
    )
)

(defn valid-line?
    "checks if line is valid rule or fact"
    [line]
    (or (valid-rule? line) (valid-fact? line))
)


(defn valid-database?
    "checks if input has valid fact format"
    [database]
    (every? valid-line? database)
)
