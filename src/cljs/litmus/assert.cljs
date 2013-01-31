(ns litmus.assert
  (:use [litmus.assert.syntax :only [check-arrow]]))

(def => "=>")

(defn equals?
  "Checks that two values are equal. Requires arrow form,
   throws exception if => is not provided.

   Example: (equals? (+ 2 2) => 4)"
  [expected arrow actual]
  (check-arrow arrow)
  (.equal chai.assert expected actual))
