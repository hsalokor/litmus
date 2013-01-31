(ns litmus.test.assert
  (:use [litmus.assert :only [=> equals? ok?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws?]]))

(defn throw-exception [] (throw (js/Error. "Error occured")))

(describe "Assert (throws? (.. form ..) => Exception #\"regexp\")"
          (given "function throwing exception"
                 (then "exception is correctly handled"
                       (throws? (throw-exception) => js/Error #"Error occured")
                       (throws? (throw (js/Error. "Error occured"))
                                => js/Error #"Error occured")))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (throws? (+ 2 2 ) 1 2 "invalid")
                                => js/Error #"Invalid.*=>.*"))))

(describe "Assert (equals? actual => expected)"
          (given "function (+ 2 2)"
                 (then "it returns 4"
                       (equals? (+ 2 2) => 4)))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (equals? (+ 2 2) 4) => js/Error #"Invalid.*=>.*"))))

(describe "Assert (ok? actual)"
          (given "truthy values"
                 (then "boolean passes" (ok? true))
                 (then "string passes" (ok? "String"))
                 (then "map passes" (ok? {:key val}))
                 (then "empty map passes" (ok? {}))
                 (then "vector passes" (ok? [])))
          (given "falsy values"
                 (then "nil fails" (throws? (ok? nil)
                                            => js/Error #"expected null.*truthy"))
                 (then "false fails" (throws? (ok? false)
                                              => js/Error #"expected false.*truthy"))))
