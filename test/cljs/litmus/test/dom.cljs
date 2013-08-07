(ns litmus.test.dom
  (:use [litmus.assert :only [=> equals?]]
        [litmus.dom :only [does-exist? match-count]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws? not-throws?]]))

(describe "DOM assertions"
          (given "calling does-exist? for existing element #my-node"
                 (then "it passes check for selector #my-node if result should be true"
                       (does-exist? "#my-node" => true))
                 (then "it throws assertion failure for selector #my-node if result should be false"
                       (throws? (does-exist? "#my-node" => false)
                                => js/Error #".*Expected selector #my-node to match no elements.*")))
          (given "calling does-exist? for non-existing #not-here node"
                 (then "it throws assertion failure for selector #not-here if result should be true"
                       (throws? (does-exist? "#not-here" => true)
                                => js/Error #".*Expected selector #not-here to match at least one element.*"))
                 (then "it passes check for selector #not-found if result should be false"
                       (does-exist? "#not-here" => false)))
          (given "calling match-count for 3 nodes with class .items"
                 (then "it passes check for selector .items if result should be 3"
                       (match-count ".items" => 3))
                 (then "it passes check for selector .no-items with expected result of 0"
                       (match-count ".no-items" => 0))
                 (then "it throws assertion failure for selector .no-items with expected result of 3"
                       (throws? (match-count ".no-items" => 3)
                                => js/Error #".*expected 0 to equal 3.*"))))
