(ns litmus.mocks.macros
  (:use clojure.tools.trace))

(defn validate [mapping]
  (if-not (= (second mapping) '=>)
    (throw (Exception. "Mock mappings must be in form (fn arg arg arg...) => result"))
    mapping))

(defn separate [mapping]
  (let [[[func & args] _ result] mapping]
    (list func args result)))

(defn convert [mapping]
  (->> (partition 3 mapping)
       (map validate)
       (map separate)))

(defmacro with-mocks [mock-mapping & body]
  (let [mappings# (convert mock-mapping)]
    `(do (let [mocks# (mapv litmus.mocks/setup mappings#)]
           (try ~@body
                (mapv litmus.mocks/verify mocks#)
                (finally
                  (mapv litmus.mocks/restore mocks#)))))))
