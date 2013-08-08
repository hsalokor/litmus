(ns litmus.test.assert
  (:use [litmus.assert :only [=> equals? not-equals? ok?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws? not-throws?]]))

(defn throw-exception [] (throw (js/Error. "Error occured")))

(describe "Value and exception asserts"
  (given "(throws? (.. form ..) => Exception #\"regexp\")"
    (then "function not throwing exception fails the check"
      (throws? (throws? (+ 1 1) => js/Error #"lol") => js/Error #"expected.*to throw.*"))
    (then "function throwing exception exception is correctly handled"
      (throws? (throw-exception) => js/Error #"Error occured")
      (throws? (throw (js/Error. "Error occured")) => js/Error #"Error occured"))
    (then "invalid form causes exception"
      (throws? (throws? (+ 2 2 ) 2 2 "invalid") => js/Error #"Invalid.*=>.*")))
  (given "(not-throws? (.. form ..) => Exception)"
    (then "function not throwing exception passes check"
      (not-throws? (+ 1 1) => js/Error))
    (then "function throwing exception fails the check"
      (throws? (not-throws? (throw (js/Error. "Error occured")) => js/Error) => js/Error #"expected.*to not throw.*"))
    (then "invalid form throws exception"
      (throws? (not-throws? (+ 2 2 ) 2 "invalid") => js/Error #"Invalid.*=>.*")))
  (given "(equals? actual => expected)"
    (then "function (+ 2 2) returns 4" (equals? (+ 2 2) => 4))
    (then "list [1 2 3] compares to [1 2 3] correctly" (equals? [1 2 3] => [1 2 3]))
    (then "list [1 2 3] produces correct error if compared to [3 2 1]"
      (throws? (equals? [1 2 3] => [3 2 1]) => js/Error #".*\[ 1, 2, 3 \] to .*\[ 3, 2, 1 \]"))
    (then "invalid form causes exception"
      (throws? (equals? (+ 2 2) "lol" 4) => js/Error #"Invalid.*=>.*")))
  (given "(not-equals? actual => expected)"
    (then "function (+ 2 2) does not return 3" (not-equals? (+ 2 2) => 3))
    (then "invalid form throws exception"
      (throws? (not-equals? (+ 2 2) "lol" 4) => js/Error #"Invalid.*=>.*")))
  (given "(ok? actual [message])"
    (then "truthy boolean passes" (ok? true))
    (then "truthy string passes" (ok? "String"))
    (then "truthy map passes" (ok? {:key val}))
    (then "empty map passes as ok" (ok? {}))
    (then "vector passes as ok" (ok? []))
    (then "nil fails" (throws? (ok? nil) => js/Error #"expected null.*truthy"))
    (then "false fails" (throws? (ok? false) => js/Error #"expected false.*truthy"))
    (then "nil fails with correct message"
      (throws? (ok? nil "value was nil") => js/Error #"value was nil"))
    (then "false fails with correct message"
      (throws? (ok? false "value was false") => js/Error #"value was false"))))
