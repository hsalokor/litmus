(ns litmus.dom
  (:use [jayq.core :only [$ is css text has-class]]
        [litmus.assert.syntax :only [check-arrow]])
  (:refer-clojure :exclude [is]))

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

(defn is-visible?
  "Check that element with given selector is visible. Visibility in this
   context means that item is visible if it is :visible or is not hidden
   (by style definition 'visibility: hidden').

   Requires arrow form,throws exception if => is not provided.

   Selector can be any jQuery compatible selector in string format.

   Example: (is-visible? \"#my-element\" => true)"
  [selector arrow result]
  (check-arrow arrow)
  (.equal chai.assert
          (and (-> ($ selector) (is ":visible"))
               (-> ($ selector) (css "visibility") (not= "hidden")))
          result
          (str "Element returned by selector "
               selector
               (if result " is not visible" " is visible"))))

(defn is-enabled?
  "Check that element with given selector is enabled. In this context
   it means that the element is not :disabled.

   Requires arrow form,throws exception if => is not provided.

   Selector can be any jQuery compatible selector in string format.

   Example: (is-enabled? \"#my-element\" => true)"
  [selector arrow result]
  (check-arrow arrow)
  (.equal chai.assert (-> ($ selector) (is ":disabled") not)
          result
          (str "Element returned by selector "
               selector
               (if result " is not enabled" " is enabled"))))

(defn has-text?
  "Check if element identified by selector has expected text.

   Example: (has-text? ($ \"#my-node\") \"my text\" => true)"
  [selector expected-text arrow result]
  (check-arrow arrow)
  (if result
    (.equal chai.assert (text ($ selector)) expected-text)
    (.notEqual chai.assert (text ($ selector)) expected-text)))

(defn has-class?
  "Check if element identified by selector has expected class.

   Example: (has-class? ($ \"#my-node\") \"my-class\" => true)"
  [selector expected-class arrow result]
  (check-arrow arrow)
  (if result
    (.isTrue chai.assert (has-class ($ selector) expected-class)
             (format "Element selected by selector %s did not have class %s" selector expected-class))
    (.isFalse chai.assert (has-class ($ selector) expected-class)
             (format "Element selected by selector %s did have class %s" selector expected-class))))
