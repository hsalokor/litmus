(ns litmus.test.dom
  (:require [litmus.dom :as dom])
  (:use [litmus.assert :only [=> equals?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws? not-throws?]]))

(describe "DOM assertions"
          (given "calling exists? for existing element #my-node"
                 (then "it passes check for selector #my-node if result should be true"
                       (dom/exists? "#my-node" => true))
                 (then "it throws assertion failure for selector #my-node if result should be false"
                       (throws? (dom/exists? "#my-node" => false)
                                => js/Error #".*Expected selector #my-node to match no elements.*")))
          (given "calling exists? for non-existing #not-here node"
                 (then "it throws assertion failure for selector #not-here if result should be true"
                       (throws? (dom/exists? "#not-here" => true)
                                => js/Error #".*Expected selector #not-here to match at least one element.*"))
                 (then "it passes check for selector #not-found if result should be false"
                       (dom/exists? "#not-here" => false))))
