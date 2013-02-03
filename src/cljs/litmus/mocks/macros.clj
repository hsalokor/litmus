(ns litmus.mocks.macros
  (:use clojure.tools.trace))

(defn validate [mapping]
  (let [[_ arrow _] mapping]
    (if-not (= (first arrow) '=>)
      (throw (Exception. "Mock mappings must be in form (fn arg arg arg... => result)"))
      mapping)))

(defn separate [mapping]
  (let [[func & args] (first mapping)
        result (last mapping)]
    (list func args result)))

(defn partition-by-arrow [mapping]
  (partition-by #(= '=> %) mapping))

(defn convert [mappings]
  (->> (map partition-by-arrow mappings)
       (map validate)
       (map separate)))

(defmacro provided [mock-mapping & body]
  (let [mappings# (convert mock-mapping)]
    `(do (let [mocks# (mapv litmus.mocks/setup mappings#)]
           (try ~@body
                (mapv litmus.mocks/verify mocks#)
                (finally
                  (mapv litmus.mocks/restore mocks#)))))))
