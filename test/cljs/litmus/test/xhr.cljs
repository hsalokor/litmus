(ns litmus.test.xhr
  (:use [litmus.assert :only [=> equals? not-equals? ok?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.xhr :only [with-json-xhr]]
               [jayq.macros :only [let-ajax]]))

(describe "with-json-xhr"
          (given "configured with single predefined response"
                 (then "it returns it correctly with HTTP GET"
                       (with-json-xhr [["GET" "/myurl" => 200 {:lol "bal"}]] 
                         (equals? true => true)
                         (let-ajax [response {:url "/myurl"
                                              :type :get
                                              :dataType "json"
                                              :contentType "application/json"}]
                                   (equals? response => "Lol"))))))
