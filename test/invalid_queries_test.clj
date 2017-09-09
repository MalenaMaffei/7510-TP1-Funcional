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
)

