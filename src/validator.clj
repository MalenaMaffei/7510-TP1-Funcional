(ns validator
    (:require [clojure.string :refer :all])    
)

(def fact-syntax "[a-z\\-]+\\([a-z\\-]+(, [a-z\\-]+){0,}\\)")

(def rule-syntax (str fact-syntax " :\\- " fact-syntax "(, " fact-syntax "){0,}"))

(defn delimit-pattern
    "delimits a regex pattern"
    [pattern]
    (str "^" pattern "$")    
)

(defn valid-fact?
    "checks if input has valid fact format"
    [fact]
    (if (re-find (re-pattern (delimit-pattern fact-syntax)) fact)
        true
        false
    )
)

(defn valid-rule?
    "checks if input has valid rule format"
    [rule]
    (if (re-find (re-pattern (delimit-pattern rule-syntax)) rule)
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
    (map valid-line? (split-lines database))
)

