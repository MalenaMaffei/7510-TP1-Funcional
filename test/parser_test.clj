(ns parser-test
  (:require [clojure.test :refer :all]
            [parser :refer :all]))


(def database (list "varon(juan)" "padre(juan, pepe)" "hola(que, tal)" "saludar(X, Y) :- hola(X), hola(Y)"))
(def text-database "
  varon(juan).
  padre(juan, pepe).
  hola(que, tal).
  saludar(X, Y) :- hola(X), hola(Y).
")


(deftest parser-test
    (testing "database should be equal to text-database after parsing"
        (is (= (get-database text-database)
            database)
        )
    )

    (testing "fact-exists varon(juan) should be true"
        (is (= (fact-exists? database "varon(juan)")
            true)
        )
    )

    (testing "fact-exists varon(marta) should be false"
        (is (= (fact-exists? database "varon(marta)")
            false)
        )
    )

    (testing "fact-exists persona(marta) should be false"
        (is (= (fact-exists? database "persona(marta)")
            false)
        )
    )

    (testing "get-params 'hola(que, tal)' should equal ('que' 'tal')"
        (is (= (get-params "hola(que, tal)")
            (list "que" "tal"))
        )
    )

    (testing "get-params 'hola(que)' should equal ('que')"
        (is (= (get-params "hola(que)")
            (list "que"))
        )
    )

    (testing "fill-rule 'saludar(X, Y) :- hola(X), hola(Y)' ('X' 'Y')
            ('juan' 'marta') should equal
            'saludar(juan, marta) :- hola(juan), hola(marta)'"
        (is (= (fill-rule "saludar(X, Y) :- hola(X), hola(Y)" (list "X" "Y") (list "juan" "marta"))
            "saludar(juan, marta) :- hola(juan), hola(marta)")
        )
    )

    (testing "fill-rule 'hijo(X, Y) :- varon(X), padre(Y, X)' ('X' 'Y')
            ('juan' 'marta') should equal
            'hijo(juan, marta) :- varon(juan), padre(marta, juan)'"
        (is (= (fill-rule "hijo(X, Y) :- varon(X), padre(Y, X)" (list "X" "Y") (list "juan" "marta"))
            "hijo(juan, marta) :- varon(juan), padre(marta, juan)")
        )
    )

    (testing "get-rule-facts 'saludar(X, Y) :- hola(X), hola(Y)' ('X' 'Y')
            ('juan' 'marta') should equal
            ['varon(juan)' 'padre(marta, juan)']"
        (is (= (get-rule-facts "hijo(X, Y) :- varon(X), padre(Y, X)" (list "X" "Y") (list "juan" "marta"))
            ["varon(juan)" "padre(marta, juan)"])
        )
    )

    (testing "get-rule-def 'saludar' should equal 'saludar(X, Y) :- hola(X), hola(Y)'"
        (is (= (get-rule-def database "saludar")
            "saludar(X, Y) :- hola(X), hola(Y)")
        )
    )

)
