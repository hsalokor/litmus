(ns litmus.xhr)

(defmacro with-json-xhr [mappings & body]
  `(do
     (.setup js/smoax)
     (doseq [[http-method# url# arrow# return-code# response#] ~mappings]
       (litmus.assert.syntax/check-arrow arrow#)
       (.register js/smoax http-method# url# (.stringify js/JSON (cljs.core/clj->js response#))))
     ~@body
     (.release js/smoax)))
