(ns litmus.mocks.macros
  (:use clojure.tools.trace))

(defn validate [mapping]
  (let [[_ arrow _] mapping]
    (if-not (= (first arrow) '=>)
      (throw (Exception. "Mock mappings must be in form (fn arg arg arg... => result)"))
      mapping)))

(defn separate [mapping]
  (let [[func args arrow result] mapping]
    [func args result]))

(defn partition-by-arrow [mapping]
  (partition-by #(= '=> %) mapping))

(defn convert [mapping]
  (->> mapping
       (partition-by-arrow)
       (validate)
       (separate)))

(defmacro provided [mock-mapping & body]
  (let [[first# & rest#] mock-mapping]
    (if first#
      (let [mapping# (convert first#)]
        `(do
           (.log js/console ~mapping#)
           (let [mock# (litmus.mocks/setup ~mapping#)]
               (try (provided ~rest# ~body)
                    (litmus.mocks/verify mock#)
                    (finally
                      (litmus.mocks/restore mock#))))))
      `(do ~@body))))
