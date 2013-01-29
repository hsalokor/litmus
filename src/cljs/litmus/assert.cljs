(ns litmus.assert
  (:use [litmus.assert.syntax :only [check-arrow]]))

(def => "=>")

(defn equals?
  [expected arrow actual]
  (check-arrow arrow)
  (.equal chai.assert expected actual))
