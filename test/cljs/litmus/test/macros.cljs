(ns litmus.test.macros
  (:use [litmus.assert :only [=> equals?]])
  (:use-macros [litmus.macros :only [describe given then it]]))

(describe "Test form"

          (given "with describe-given-then form"
                 (then "is bypassed if no assertions exist")
                 (then "executes single assertion inside"
                       (equals? 1 => 1)
                       (equals? 2 => 2))
                 (then "executes multiple assertions inside"
                       (equals? 1 => 1)
                       (equals? 2 => 2)))

          (describe "with describe-(describe-)it form"
                 (it "is bypassed if no assertions exist")
                 (it "executes single assertion inside"
                       (equals? 1 => 1)
                       (equals? 2 => 2))
                 (it "executes multiple assertions inside"
                       (equals? 1 => 1)
                       (equals? 2 => 2))))
