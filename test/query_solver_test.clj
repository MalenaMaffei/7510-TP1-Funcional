(ns query-solver-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))


(def db (list
  "varon(juan)"
  "padre(juan, pepe)"
  "hola(que)"
  "hola(tal)"
  "saludar(X, Y) :- hola(X), hola(Y)")
)

(deftest solve-query-test
    (testing "solve-query saludar(que, tal) should be true"
        (is (= (solve-query db "saludar(que, tal)")
            true)
        )
    )

    (testing "solve-query saludar(que) should be nil"
        (is (= (solve-query db "saludar(que)")
            nil)
        )
    )

    (testing "solve-query saludar(hola, que, tal) should be nil"
        (is (= (solve-query db "saludar(hola, que, tal)")
            nil)
        )
    )

    (testing "solve-query greet(que) should be false"
        (is (= (solve-query db "greet(que)")
            false)
        )
    )
)
