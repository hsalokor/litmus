(ns litmus.test.mocks
  (:use [litmus.assert :only [=> equals?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.mocks.macros :only [provided]]))

(defn test-fn [number]
  (+ 5 number))

(describe "A mock"
  (given "installed with with-mocks macro"
    (then "verifies that function is called"
      (provided [(test-fn 1 => 2)
                 (test-fn 2 => 4)]
        (equals? (test-fn 1) => 2)
        (equals? (test-fn 2) => 4)))))
