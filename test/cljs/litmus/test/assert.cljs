(ns litmus.test.assert
  (:use [litmus.assert :only [equals? =>]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws?]]))

(describe "Assert (equals? actual => expected)"
          (given "function (+ 2 2)"
                 (then "it returns 4"
                       (equals? (+ 2 2) => 4)))
          (given "invalid form"
                 (then "it throws exception"
                       (throws? (equals? (+ 2 2) 4) => js/Error #"Invalid.*=>.*"))))
