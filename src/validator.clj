(ns validator)

(def fact-syntax "[a-z\\-]+\\([a-z\\-]+(, [a-z\\-]+){0,}\\)")



(defn is-valid-fact?
    "checks if input has valid fact format"
    [fact]
    (if (re-find (re-pattern (str "^" fact-syntax "$")) fact)
        true
        false
    )
)

(defn is-valid-database?
    "checks if input has valid fact format"
    [database]
    (if (re-find (re-pattern (str "^" fact-syntax "\\.$")) database)
        true
        false
    )
)