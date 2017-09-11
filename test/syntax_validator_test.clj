(ns syntax-validator-test
  (:require [clojure.test :refer :all]
            [syntax-validator :refer :all]))


(def complete-invalid-database (list "varon(juan)" "padre(juan,pepe)" "hola(que, tal)" "saludar :- hola(X), hola(Y)"))

(def incomplete-database (list "varon(juan)" "varon"))

(def complete-valid-database (list "varon(juan)" "padre(juan, pepe)" "hola(que, tal)" "saludar(X, Y) :- hola(X), hola(Y)"))



(deftest facts-syntax-test
    (testing "varon(juan) should be true"
        (is (= (valid-fact? "varon(juan)")
            true)
        )
    )

    (testing "varon(juan) should be false"
        (is (= (valid-fact? "varon(juan )")
            false)
        )
    )

    (testing "varon(juan, ) should be false"
        (is (= (valid-fact? "varon(juan, )")
            false)
        )
    )

    (testing "amigos(juan, pepe, adrian, elias) should be true"
        (is (= (valid-fact? "amigos(juan, pepe, adrian, elias)")
            true)
        )
    )

    (testing "varon(juan,pepe) should be false"
        (is (= (valid-fact? "varon(juan,pepe)")
            false)
        )
    )

    (testing "varon(juan, pepe ) should be false"
        (is (= (valid-fact? "varon(juan, pepe )")
            false)
        )
    )
)

(deftest rules-syntax-test
    (testing "varon(X) should be false"
        (is (= (valid-rule? "varon(X)")
            false)
        )
    )

    (testing "varon(X) :-  should be false"
        (is (= (valid-rule? "varon(X) :- ")
            false)
        )
    )

    (testing "varon(x) :- hola(y) should be false"
        (is (= (valid-rule? "varon(x) :- hola(y)")
            false)
        )
    )

    (testing "varon(X):-hola(Y) should be false"
        (is (= (valid-rule? "varon(X):-hola(Y)")
            false)
        )
    )

    (testing "varon :- hola(Y) should be false"
        (is (= (valid-rule? "varon :- hola(Y)")
            false)
        )
    )

    (testing "varon(XY):-hola(Y) should be false"
        (is (= (valid-rule? "varon(XY):-hola(Y)")
            false)
        )
    )

    (testing "hijo(X, Y) :- varon(X), padre(Y, X). should be true"
        (is (= (valid-rule? "hijo(X, Y) :- varon(X), padre(Y, X)")
            true)
        )
    )
)

(deftest database-test
    (testing "complete db with invalid facts and rules should be false"
      (is (= (valid-database? complete-invalid-database)
          false)
      )
    )

    (testing "complete db with valid facts and rules should be true"
      (is (= (valid-database? complete-valid-database)
          true)
      )
    )

    (testing "incomplete db should be false"
      (is (= (valid-database? incomplete-database)
          false)
      )
    )

)
