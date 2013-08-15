(ns litmus.test.xhr
  (:require [jayq.core])
  (:use [litmus.assert :only [=> equals? not-equals? ok?]]
        [litmus.xhr :only [ajax-called? ajax-not-called? ajax-called-with? ajax-called-with-json?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.assert.macros :only [throws?]]
               [litmus.macros.xhr :only [with-json-mocks]]
               [jayq.macros :only [let-ajax]]))

(defn throw-exception [xhr status error] (throw (js/Error. (str "Error occured: " status " / " error))))

(describe "Mocking $.ajax"
  (given "configured with single predefined response"
    (then "returns response correctly for HTTP GET"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl" :dataType :json :error throw-exception}]
          (ajax-called? => true)
          (equals? response => (clj->js {:lol "bal"})))))
    (then "returns it correctly for HTTP POST"
      (with-json-mocks [["POST" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"
                             :method :post
                             :data (clj->js {:foo "bar"})
                             :dataType :json
                             :error throw-exception}]
          (ajax-called? => true)
          (equals? response => (clj->js {:lol "bal"})))))
    (then "returns response correctly for HTTP PUT"
      (with-json-mocks [["PUT" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"
                             :method :put
                             :data (clj->js {:foo "bar"})
                             :dataType :json
                             :error throw-exception}]
          (ajax-called? => true)
          (equals? response => (clj->js {:lol "bal"})))))
    (then "returns response correctly for HTTP DELETE"
      (with-json-mocks [["DELETE" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"
                             :method :delete
                             :dataType :json
                             :error throw-exception}]
          (ajax-called? => true)
          (equals? response => (clj->js {:lol "bal"})))))
    (then "throws error when incorrect form is used"
      (throws? (with-json-mocks [["DELETE" "/myurl" => {:lol "bal"}]])
               => js/Error "Return code must be a number, but it was {:lol \"bal\"}")))
  (given "$.ajax call asserts"
    (then "ajax-called? => true (any call) passes check if HTTP call is made"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl" :dataType :json :error throw-exception}]
          (ajax-called? => true))))
    (then "ajax-called? => false (any call) passes check if no HTTP call is made"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (ajax-called? => false)))
    (then "ajax-called? => true (any call) fails if no HTTP call is made"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (throws? (ajax-called? => true)
                 => js/Error #"expected ajax to have been invoked")))
    (then "ajax-called? => false (any call) fails if HTTP call is made"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl" :dataType :json :error throw-exception}]
          (throws? (ajax-called? => false)
                   => js/Error #"expected ajax not to have invoked"))))
    (then "ajax-called-with? GET /myurl nil => true passes only when call is made with specific parameters"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"}]
          (ajax-called-with? "GET" "/myurl" nil => true)
          (throws? (ajax-called-with? "GET" "/wrongurl" nil => true)
                   => js/Error #"Expected GET /wrongurl to have been invoked. There have been calls to: GET /myurl"))))
    (then "ajax-called-with? GET /myurl nil => true fails when no call is made"
      (with-json-mocks [["GET" "/myurl" => 200 {:lol "bal"}]]
        (throws? (ajax-called-with? "GET" "/myurl" nil => true)
                 => js/Error #"Expected GET /myurl to have been invoked. There have been no ajax calls.")))
    (then "ajax-called-with? POST /myurl mydata => true passes when HTTP call is made"
      (with-json-mocks [["POST" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"
                             :method :post
                             :dataType :json
                             :data "mydata"
                             :error throw-exception}]
          (ajax-called-with? "POST" "/myurl" "mydata" => true))))
    (then "ajax-called-with? POST /wrongurl mydata => true fails"
      (with-json-mocks [["POST" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"
                             :method :post
                             :dataType :json
                             :data "mydata"
                             :error throw-exception}]
          (throws? (ajax-called-with? "POST" "/wrongurl" "mydata" => true)
                   => js/Error #"Expected POST /wrongurl to have been invoked. There have been calls to: POST /myurl"))))
    (then "ajax-called-with? POST /myurl wrongdata => true fails"
      (with-json-mocks [["POST" "/myurl" => 200 {:lol "bal"}]]
        (let-ajax [response {:url "/myurl"
                             :method :post
                             :dataType :json
                             :data "mydata"
                             :error throw-exception}]
          (throws? (ajax-called-with? "POST" "/myurl" "wrongdata" => true)
                   => js/Error #"Expected POST /myurl to have been invoked with \'wrongdata\' but was invoked with \'mydata\'"))))
    (then "ajax-called-with-json? POST /myurl {:my \"data\"} => true passes"
      (with-json-mocks [["POST" "/myurl" => 200 {:my "data"}]]
        (let-ajax [response {:url "/myurl"
                             :method :post
                             :dataType :json
                             :data "{\"my\":\"data\"}"
                             :error throw-exception}]
          (ajax-called-with-json? "POST" "/myurl" {:my "data"} => true))))))
