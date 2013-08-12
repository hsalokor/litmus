(ns litmus.xhr
  (:use [litmus.assert.syntax :only [check-arrow]]))

(defn- been-invoked? []
  (try (-> (js/chai.Assertion. js/smoax)
           (.beenInvoked))
       true
    (catch js/Error msg
      false)))

(defn- been-invoked-with? [method url data]
  (try (-> (js/chai.Assertion. js/smoax)
           (.beenInvokedWith method url data))
       true
    (catch js/Error msg
      false)))

(defn ajax-called?
  "This assertion checks if the code inside the mock makes any AJAX call.

   Example: (with-json-mocks [[...]]
              (let-ajax [...]
                (ajax-called? => true)))"
  [arrow result]
  (check-arrow arrow)
  (when (not= (been-invoked?) result)
    (.assert js/chai false
             (if result
               "Expected the ajax to have been invoked, but it was not"
               "Expected the ajax not to have been invoked, but it was"))))

(defn ajax-called-with?
  "This assertion checks if the code inside the mock makes AJAX call with
   to given URL, with given verb and with given payload.

   Example: (with-json-mocks [[...]]
              (let-ajax [response {:url \"url\" ...}]
                 (ajax-called-with \"GET\" \"url\" nil => true)))"
  [method url data arrow result]
  (check-arrow arrow)
  (when (not= (been-invoked-with? method url data) result)
    (.assert js/chai false
             (if result
               (format "Expected the URL %s to have been invoked with method %s and data %s, but it was not."
                       url method data)
               (format "Expected the URL %s not to have been invoked with method %s and data %s, but it was"
                       url method data)))))

(defn ajax-called-with-json?
  "This assertion checks if the code inside the mock makes AJAX call with
   to given URL, with given verb and with given JSON payload (given as clojure map).

   Example: (with-json-mocks [[...]]
              (let-ajax [response {:url \"url\" ...}]
                 (ajax-called-with-json? \"GET\" \"url\" {:lol \"bal\"} => true)))"
  [method url data arrow result]
  (ajax-called-with? method url (.stringify js/JSON (clj->js data)) arrow result))
