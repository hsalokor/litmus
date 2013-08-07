(ns litmus.dom
  (:use [jayq.core :only [$]]
        [litmus.assert.syntax :only [check-arrow]]))

(defn match-count
  "Checks that given number of matches with given selector are found. Requires arrow form,
   throws exception if => is not provided.

   Selector can be any jQuery compatible selector in string format.

   Example: (match-count \"#my-node .element\" => 3)"
  [selector arrow result]
  (check-arrow arrow)
  (.equal chai.assert (count ($ selector)) result))

(defn does-exist?
  "Checks that element with given selector is found. Requires arrow form,
   throws exception if => is not provided.

   Selector can be any jQuery compatible selector in string format.

   Example: (does-exist? \"#my-element\" => true)"
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
