(ns litmus.test.mocks
  (:use [litmus.assert :only [=> equals?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.mocks.macros :only [with-mocks]]))

(defn test-fn [number]
  (+ 5 number))

(describe "A mock"
          (given "installed with with-mocks macro"
                 (then "verifies that function is called"
                       (with-mocks [(test-fn 1) => 2]
                         (equals? (test-fn 1) => 2)))))
