(ns litmus.test.dom
  (:use [jayq.core :only [$ show hide]]
        [litmus.assert :only [=> equals?]]
        [litmus.dom :only [does-exist? match-count is-visible? is-enabled? has-text? has-class?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws? not-throws?]]))

(describe "DOM assertions"
  (given "does-exist? for existing element #my-node"
    (then "passes check for selector #my-node if result should be true"
      (does-exist? "#my-node" => true))
    (then "throws assertion failure for selector #my-node if result should be false"
      (throws? (does-exist? "#my-node" => false)
               => js/Error #".*Expected selector #my-node to match no elements.*")))
  (given "does-exist? for non-existing #not-here node"
    (then "throws assertion failure for selector #not-here if result should be true"
      (throws? (does-exist? "#not-here" => true)
               => js/Error #".*Expected selector #not-here to match at least one element.*"))
    (then "passes check for selector #not-found if result should be false"
      (does-exist? "#not-here" => false)))
  (given "match-count for 3 nodes with class .items"
    (then "passes check for selector .items if result should be 3"
      (match-count ".items" => 3))
    (then "passes check for selector .no-items with expected result of 0"
      (match-count ".no-items" => 0))
    (then "throws assertion failure for selector .no-items with expected result of 3"
      (throws? (match-count ".no-items" => 3)
               => js/Error #".*expected 0 to equal 3.*")))
  (given "is-visible? to visible #my-node node"
    (then "passes check for selector #my-node with expected result true"
      (is-visible? "#my-node" => true))
    (then "throws assertion failure for selector #my-node with expected result false"
      (throws? (is-visible? "#my-node" => false)
               => js/Error #".*Element returned by selector.*is visible.*")))
  (given "is-visible? to hidden nodes #hidden and #display-none"
    (then "passes check for selector #hidden with expected result false"
      (is-visible? "#hidden" => false))
    (then "passes check for selector #display-none with expected result false"
      (is-visible? "#display-none" => false))
    (then "is compatible with jQuery hide and show"
      (is-visible? "#runtime-hiding" => true)
      (hide ($ "#runtime-hiding"))
      (is-visible? "#runtime-hiding" => false)
      (show ($ "#runtime-hiding"))
      (is-visible? "#runtime-hiding" => true))
    (then "produces correct results for child elements"
      (is-visible? "#runtime-hiding" => true)
      (is-visible? "#runtime-hiding-child" => true)
      (hide ($ "#runtime-hiding"))
      (is-visible? "#runtime-hiding" => false)
      (is-visible? "#runtime-hiding-child" => false)
      (show ($ "#runtime-hiding"))
      (is-visible? "#runtime-hiding" => true)
      (is-visible? "#runtime-hiding" => true)
      (is-visible? "#runtime-hiding-child" => true)))
  (given "is-enabled? to button elements #disabled-button and #enabled-button"
    (then "passes check for selector #disabled-button with expected result false"
      (is-enabled? "#disabled-button" => false))
    (then "passes check for selector #enabled-button with expected result true"
      (is-enabled? "#enabled-button" => true))
    (then "throws assertion error for selector #disabled-button with expected result true"
      (throws? (is-enabled? "#disabled-button" => true)
               => js/Error #".*Element returned by selector.*is not enabled.*"))
    (then "throws assertion error for selector #enabled-button with expected result false"
      (throws? (is-enabled? "#enabled-button" => false)
               => js/Error #".*Element returned by selector.*is enabled.*")))
  (given "has-text?"
    (then "passes check for selector #text-node"
      (has-text? "#text-node" "Test text" => true))
    (then "fails check for selector #no-node"
      (throws? (has-text? "#no-node" "Test text" => true)
               => js/Error #"expected '' to equal 'Test text'")))
  (given "has-class?"
    (then "passes check for node #test-item when expected result is true"
      (has-class? "#test-item" "items" => true))
    (then "fails check for node #test-item when expected result is true"
      (throws? (has-class? "#test-item" "items" => false)
               => js/Error #"Element selected by selector #test-item did have class items.*"))
    (then "fails check for selector #my-node when expected result is true"
      (throws? (has-class? "#my-node" "items" => true)
               => js/Error #"Element selected by selector #my-node did not have class items.*"))
    (then "passes check for selector #my-node when expected result is false"
      (has-class? "#my-node" "items" => false))))
