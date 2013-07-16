(ns litmus.xhr)

(defmacro with-json-xhr [mappings & body]
  `(do
     (let [xhr# (.create js/sinon.fakeServer)]
       (doseq [[http-method# url# arrow# return-code# response#] ~mappings]
         (.log js/console (str "http-method: " (pr-str http-method#)))
         (.log js/console (str "url: " (pr-str url#)))
         (.log js/console (str "arrow: " (pr-str arrow#)))
         (.log js/console (str "return-code: " (pr-str return-code#)))
         (.log js/console (str "response: " (pr-str response#)))
         (litmus.assert.syntax/check-arrow arrow#)
         (.respondWith xhr# http-method# url# [return-code#
                                               (clj->js {"Content-Type" "application/json"})
                                               (clj->js response#)]))
       (fn [] ~@body)
       (.restore xhr#))))
