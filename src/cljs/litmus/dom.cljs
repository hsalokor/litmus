(ns litmus.dom
  (:use [jayq.core :only [$]]
        [litmus.assert.syntax :only [check-arrow]]))

(defn exists?
  "Checks that element with given selector is found. Requires arrow form,
   throws exception if => is not provided.

   Selector can be any jQuery compatible selector in string format.

   Example: (exists? \"#my-element\" => true)"
  [selector arrow result]
  (check-arrow arrow)
  (let [elem-count (count ($ selector))]
    (if result
      (.notEqual chai.assert elem-count 0
                 (str "Expected selector "
                      selector
                      " to match at least one element, but element was not found"))
      (.equal chai.assert elem-count 0
              (str "Expected selector "
                   selector
                   " to match no elements, but at least one was found")))))
