(ns validator)

(defn is-valid-fact?
    "checks if input has valid fact format"
    [fact]
    (if (re-find #"^[a-z\-]+\([a-z\-]+(, [a-z\-]+){0,}\)$" fact)
        true
        false
    )
)