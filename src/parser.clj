(ns parser (:require [clojure.string :refer :all])
(:require [syntax-validator :refer :all]))

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

(defn get-params
  [query]
  (split (second (re-matches #"^[a-z]+\(([^\)]+)\).*$" query)) #", ")
)

(defn fill-rule
  [rule-definition rule-params query-params]
  (def to-replace (join "|" rule-params))
  (def replacement-map (doall (zipmap rule-params query-params)))
  (apply str (clojure.string/replace rule-definition (re-pattern to-replace) replacement-map))
)

(defn get-rule-facts
  [rule-definition rule-params query-params]
  (def filled-rule (fill-rule rule-definition rule-params query-params))
  (split (clojure.string/replace (second (re-matches #"^.+ :- ([a-z]+\(.+\))$" filled-rule)) #"(\)), " "$1.") #"\.")
)

(defn rule-fulfilled?
  [rule database]
  (def facts (split (second (re-matches #"^.+ :- ([a-z]+\(.+\))$" rule)) #", "))
  (every? fact-exists? facts)
)

(defn get-rules
  [database]
  (doall (filter valid-rule? database))
)

(defn get-rule-def
  [database query-rule]
  (first (filter (fn [x] (re-find (re-pattern (str query-rule "\\(.+\\) :- ")) x)) (get-rules database)))
)
