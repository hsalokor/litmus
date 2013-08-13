(ns litmus.xhr
  (:use [litmus.assert.syntax :only [check-arrow]]))

(defn- been-invoked? []
  (-> (js/chai.Assertion. js/smoax)
      (.beenInvoked)))

(defn- not-been-invoked? []
  (-> (js/chai.Assertion. js/smoax)
      (.-not)
      (.beenInvoked)))

(defn- been-invoked-with? [method url data]
  (-> (js/chai.Assertion. js/smoax)
      (.beenInvokedWith method url data)))

(defn- not-been-invoked-with? [method url data]
  (-> (js/chai.Assertion. js/smoax)
      (.-not)
      (.beenInvokedWith method url data)))

(defn ajax-called?
  "This assertion checks if the code inside the mock makes any AJAX call.

   Example: (with-json-mocks [[...]]
              (let-ajax [...]
                (ajax-called? => true)))"
  [arrow result]
  (check-arrow arrow)
  (if result
    (been-invoked?)
    (not-been-invoked?)))

(defn ajax-called-with?
  "This assertion checks if the code inside the mock makes AJAX call with
   to given URL, with given verb and with given payload.

   Example: (with-json-mocks [[...]]
              (let-ajax [response {:url \"url\" ...}]
                 (ajax-called-with \"GET\" \"url\" nil => true)))"
  [method url data arrow result]
  (check-arrow arrow)
  (if result
    (been-invoked-with? method url data)
    (not-been-invoked-with? method url data)))

(defn ajax-called-with-json?
  "This assertion checks if the code inside the mock makes AJAX call with
   to given URL, with given verb and with given JSON payload (given as clojure map).

   Example: (with-json-mocks [[...]]
              (let-ajax [response {:url \"url\" ...}]
                 (ajax-called-with-json? \"GET\" \"url\" {:lol \"bal\"} => true)))"
  [method url data arrow result]
  (ajax-called-with? method url (.stringify js/JSON (clj->js data)) arrow result))
