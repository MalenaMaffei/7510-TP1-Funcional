(ns invalid-queries-test
  (:require [clojure.test :refer :all]
            [validator :refer :all]))


(deftest invalid-queries-test
    (testing "varon(juan) should be true"
        (is (= (is-valid-fact? "varon(juan)")
            true)
        )
    )

    (testing "varon(juan) should be false"
        (is (= (is-valid-fact? "varon(juan )")
            false)
        )
    )
    
    (testing "varon(juan, ) should be false"
        (is (= (is-valid-fact? "varon(juan, )")
            false)
        )
    )

    (testing "amigos(juan, pepe, adrian, elias) should be true"
        (is (= (is-valid-fact? "amigos(juan, pepe, adrian, elias)")
            true)
        )
    )

    (testing "varon(juan,pepe) should be false"
        (is (= (is-valid-fact? "varon(juan,pepe)")
            false)
        )
    )

    (testing "varon(juan, pepe ) should be false"
        (is (= (is-valid-fact? "varon(juan, pepe )")
            false)
        )
    )
)

(deftest invalid-rule-test
    (testing "varon(juan) should be false"
        (is (= (is-valid-rule? "varon(juan)")
            false)
        )
    )

    (testing "varon(juan) :-  should be false"
        (is (= (is-valid-rule? "varon(juan) :- ")
            false)
        )
    )

    (testing "varon(juan) :- hola(juan) should be true"
        (is (= (is-valid-rule? "varon(juan) :- hola(juan)")
            true)
        )
    )

    (testing "varon(juan):-hola(juan) should be false"
        (is (= (is-valid-rule? "varon(juan):-hola(juan)")
            false)
        )
    )

    (testing "saludar(pepe, carlos) :- hola(pepe), hola(carlos) should be true"
        (is (= (is-valid-rule? "saludar(pepe, carlos) :- hola(pepe), hola(carlos)")
            true)
        )
    )
    
)
