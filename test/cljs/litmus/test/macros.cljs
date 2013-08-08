(ns litmus.test.macros
  (:use [litmus.assert :only [=> equals?]])
  (:use-macros [litmus.macros :only [describe given then it before after before-each after-each]]))

(def before-test-atom (atom 0))
(def after-test-atom (atom 0))
(def before-each-atom (atom 0))
(def after-each-atom (atom 0))

(describe "Test description forms"
  (given "describe-given-then form"
    (then "is bypassed if no assertions exist")
    (then "executes single assertion inside"
      (equals? 1 => 1)
      (equals? 2 => 2))
    (then "executes multiple assertions inside"
      (equals? 1 => 1)
      (equals? 2 => 2)))
  (describe "describe-(describe-)it form"
    (it "is bypassed if no assertions exist")
    (it "executes single assertion inside"
      (equals? 1 => 1)
      (equals? 2 => 2))
    (it "executes multiple assertions inside"
      (equals? 1 => 1)
      (equals? 2 => 2))))

(describe "Before hook"
  (before (swap! before-test-atom inc))
  (given "when set up in describe block"
    (then "is triggered before test"
      (equals? @before-test-atom => 1))
    (then "is triggered only once"
      (equals? @before-test-atom => 1))))

(describe "After hook"
  (describe "in first describe"
    (after (swap! after-test-atom inc))
    (it "is not triggered before exit"
      (equals? @after-test-atom => 0)))
  (describe "in second describe"
    (after (swap! after-test-atom inc))
    (it "is triggered before second test (after exiting first test)"
      (equals? @after-test-atom => 1))
    (it "<dummy test>"
      (equals? 1 => 1)))
  (describe "In third describe"
    (it "the previous describe block with 2 tests has triggered after-hook once"
      (equals? @after-test-atom => 2))))

(describe "Before-each hook"
  (before-each (swap! before-each-atom inc))
  (given "when set up in describe block"
    (then "is triggered before first test"
      (equals? @before-each-atom => 1))
    (then "is triggered before second test"
      (equals? @before-each-atom => 2))))

(describe "After-each hook"
  (after-each (swap! after-each-atom inc))
  (given "when set up in describe block"
    (then "is not triggered before first test"
      (equals? @after-each-atom => 0))
    (then "is triggered before second test"
      (equals? @after-each-atom => 1))
    (then "is triggered before third test"
      (equals? @after-each-atom => 2))))
