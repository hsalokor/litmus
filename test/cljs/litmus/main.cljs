(ns litmus.main
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
