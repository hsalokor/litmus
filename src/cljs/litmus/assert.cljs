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

(defn not-equals?
  "Checks that two values are not equal. Requires arrow form,
   throws exception if => is not provided.

   Example: (not-equals? (+ 2 2) => 3)"
  [expected arrow actual]
  (check-arrow arrow)
  (.notEqual chai.assert expected actual))

(defn ok?
  "Checks that value is ok, i.e. truthy. Accepts optional message param.

   examples: (ok? my-object)
             (ok? (my-fn a b c) \"my-fn call returned falsy\")"
  ([actual] (.ok chai.assert actual))
  ([actual message] (.ok chai.assert actual message)))
