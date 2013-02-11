(ns litmus.test.assert
  (:use [litmus.assert :only [=> equals? not-equals? ok?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws? not-throws?]]))

(defn throw-exception [] (throw (js/Error. "Error occured")))

(describe "Assert (throws? (.. form ..) => Exception #\"regexp\")"
          (given "function not throwing exception"
                 (then "fails the check"
                       (throws? (throws? (+ 1 1) => js/Error #"lol")
                                => js/Error #"expected.*to throw.*")))
          (given "function throwing exception"
                 (then "exception is correctly handled"
                       (throws? (throw-exception) => js/Error #"Error occured")
                       (throws? (throw (js/Error. "Error occured"))
                                => js/Error #"Error occured")))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (throws? (+ 2 2 ) 2 2 "invalid")
                                => js/Error #"Invalid.*=>.*"))))

(describe "Assert (not-throws? (.. form ..) => Exception)"
          (given "function not throwing exception"
                 (then "exception check passes"
                       (not-throws? (+ 1 1) => js/Error)))
          (given "function throwing exception"
                 (then "fails the check"
                       (throws? (not-throws? (throw (js/Error. "Error occured")) => js/Error)
                                => js/Error #"expected.*to not throw.*")))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (not-throws? (+ 2 2 ) 2 "invalid")
                                => js/Error #"Invalid.*=>.*"))))

(describe "Assert (equals? actual => expected)"
          (given "function (+ 2 2)"
                 (then "it returns 4"
                       (equals? (+ 2 2) => 4)))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (equals? (+ 2 2) "lol" 4) => js/Error #"Invalid.*=>.*"))))

(describe "Assert (not-equals? actual => expected)"
          (given "function (+ 2 2)"
                 (then "it does not return 3"
                       (not-equals? (+ 2 2) => 3)))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (not-equals? (+ 2 2) "lol" 4) => js/Error #"Invalid.*=>.*"))))

(describe "Assert (ok? actual [message])"
          (given "truthy values"
                 (then "boolean passes" (ok? true))
                 (then "string passes" (ok? "String"))
                 (then "map passes" (ok? {:key val}))
                 (then "empty map passes" (ok? {}))
                 (then "vector passes" (ok? [])))
          (given "falsy values"
                 (then "nil fails"
                       (throws? (ok? nil) => js/Error #"expected null.*truthy"))
                 (then "false fails"
                       (throws? (ok? false) => js/Error #"expected false.*truthy")))
          (given "falsy values with message"
                 (then "nil fails with correct message"
                       (throws? (ok? nil "value was nil")
                                => js/Error #"value was nil"))
                 (then "false fails with correct message"
                       (throws? (ok? false "value was false")
                                => js/Error #"value was false"))))
