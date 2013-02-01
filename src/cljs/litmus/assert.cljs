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

(defn ok?
  "Checks that value is ok, i.e. truthy. Accepts optional message param.

   Examples: (ok? my-object)
             (ok? (my-fn a b c) \"my-fn call failed\")"
  ([actual] (.ok chai.assert actual))
  ([actual message] (.ok chai.assert actual message)))
