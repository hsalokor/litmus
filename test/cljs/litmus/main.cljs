(ns litmus.main
  (:use [litmus.assert :only [equals? =>]])
  (:use-macros [litmus.macros :only [describe it given then]]))

; describe-given-then style
(describe "A stack"
          (given "#pop is called"
                 (then "exception is thrown if stack is empty"
                       true)
                 (then "value in the top of stack is returned"
                       false)))

; describe-it style
(describe "A list"
          (describe "#append is called"
                 (it "new item is appended to list"
                     true)))

(describe "Number comparison"
          (it "2 + 2 = 4"
              (equals? (+ 2 2) => 3)))
