(ns parser (:require [clojure.string :refer :all])
(:require [syntax-validator :refer :all]))


(defn get-database
  "parses db string and outputs it in list form"
  [text-database]
  (map trim
          (doall (filter (fn [x] (not (blank? x))) (split text-database db-delimiter)))
  )
)

(defn fact-exists?
  [database fact]
  (.contains database fact)
)

(defn get-params
  "gets parameters from fact or rule"
  [query]
  (split (second (re-matches #"^[a-z]+\(([^\)]+)\).*$" query)) param-delimiter)
)

(defn fill-rule
  "replaces all variables from rule-definition with the corresponding parametrs from the query"
  [rule-definition rule-params query-params]
  (def to-replace (join "|" rule-params))
  (def replacement-map (doall (zipmap rule-params query-params)))
  (apply str (clojure.string/replace rule-definition (re-pattern to-replace) replacement-map))
)

(defn get-rule-facts
  "gets list of facts that compose given rule"
  [rule-definition rule-params query-params]
  (def filled-rule (fill-rule rule-definition rule-params query-params))
  (split (clojure.string/replace (second (re-matches #"^.+ :- ([a-z]+\(.+\))$" filled-rule)) #"(\)), " "$1.") db-delimiter)
)

(defn get-rules
  "filters all rules from the db"
  [database]
  (doall (filter valid-rule? database))
)

(defn get-rule-def
  "gets whole rule definition from database"
  [database query-rule]
  (first (filter (fn [x] (re-find (re-pattern (str query-rule "\\(.+\\) :- ")) x)) (get-rules database)))
)

(defn valid-entries?
  "can entries be parsed?"
  [database query]
  (and (valid-database? database) (valid-fact? query))
)
