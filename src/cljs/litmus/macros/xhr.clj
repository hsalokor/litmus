(ns litmus.macros.xhr)

(defmacro with-json-mocks [mappings & body]
  `(do
     (.use js/chai (.setup js/smoax))
     (doseq [[http-method# url# arrow# return-code# response#] ~mappings]
       (litmus.assert.syntax/check-arrow arrow#)
       (if (not (number? return-code#))
         (throw (js/Error. (str "Return code must be a number, but it was " return-code#))))
       (.register js/smoax http-method# url# (.stringify js/JSON (cljs.core/clj->js response#))))
     ~@body
     (.release js/smoax)))
