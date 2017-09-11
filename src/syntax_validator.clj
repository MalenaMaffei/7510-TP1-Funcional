(ns syntax-validator
    (:require [clojure.string :refer :all])
)

(def db-delimiter #"\.")
(def param-delimiter #", ")

(def fact-syntax #"^[a-z\-]+\([a-z\-]+(, [a-z\-]+){0,}\)$")
(def rule-syntax #"^[a-z\-]+\([A-Z](, [A-Z]+){0,}\) :\- [a-z\-]+\([A-Z](, [A-Z]+){0,}\)(, [a-z\-]+\([A-Z](, [A-Z]+){0,}\)){0,}$")

;"TODO: rules tienen que ser validadas un poco mas, que las variables coincidan por lo menos

(defn valid-fact?
    "checks if input has valid fact format"
    [fact]
    (if (re-find fact-syntax fact)
        true
        false
    )
)

(defn valid-rule?
    "checks if input has valid rule format"
    [rule]
    (if (re-find rule-syntax rule)
        true
        false
    )
)

(defn valid-line?
    [line]
    (or (valid-rule? line) (valid-fact? line))
)


(defn valid-database?
    [database]
    (every? valid-line? database)
)
