(ns litmus.xhr
  (:use [litmus.assert.syntax :only [check-arrow]]))

(defn- been-invoked? []
  (try (-> (js/chai.Assertion. js/smoax)
           (.beenInvoked))
       true
    (catch js/Error msg
      false)))

(defn ajax-called? [arrow result]
  (check-arrow arrow)
  (when (not= (been-invoked?) result)
    (.assert js/chai false
             (if result
               "Expected the ajax to have been invoked, but it was not"
               "Expected the ajax not to have been invoked, but it was"))))
