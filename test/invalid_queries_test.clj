(ns invalid-queries-test
  (:require [clojure.test :refer :all]
            [validator :refer :all]))


(def incomplete-database "
	varon(juan).
    varon.
    padre(juan,pepe).
    hola(que, tal).
    saludar :- hola(juan), hola(pepe).
")
           

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
    (testing "varon(juan) should be false"
        (is (= (valid-rule? "varon(juan)")
            false)
        )
    )

    (testing "varon(juan) :-  should be false"
        (is (= (valid-rule? "varon(juan) :- ")
            false)
        )
    )

    (testing "varon(juan) :- hola(juan) should be true"
        (is (= (valid-rule? "varon(juan) :- hola(juan)")
            true)
        )
    )

    (testing "varon(juan):-hola(juan) should be false"
        (is (= (valid-rule? "varon(juan):-hola(juan)")
            false)
        )
    )

    (testing "saludar(pepe, carlos) :- hola(pepe), hola(carlos) should be true"
        (is (= (valid-rule? "saludar(pepe, carlos) :- hola(pepe), hola(carlos)")
            true)
        )
    )
)
