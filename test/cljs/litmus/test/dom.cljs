(ns litmus.test.dom
  (:require [litmus.dom :as dom])
  (:use [litmus.assert :only [=> equals?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws? not-throws?]]))

(describe "Exists? check"
          (given "for existing element #my-node"
                 (then "it passes exists? check for selector #my-node"
                       (dom/exists? "#my-node" => true))) )
