(ns litmus.test.xhr
  (:require [jayq.core])
  (:use [litmus.assert :only [=> equals? not-equals? ok?]])
  (:use-macros [litmus.macros :only [describe given then]]
               [litmus.xhr :only [with-json-xhr]]
               [jayq.macros :only [let-ajax]]))

(defn throw-exception [xhr status error] (throw (js/Error. (str "Error occured: " status " / " error))))

(describe "with-json-xhr"
          (given "configured with single predefined response"
                 (then "it returns it correctly with HTTP GET"
                       (with-json-xhr [["GET" "/myurl" => 200 {:lol "bal"}]] 
                         (let-ajax [response {:url "/myurl" :dataType :json :error throw-exception}]
                           (equals? response => (clj->js {:lol "bal"})))))))
