(ns litmus.mocks.macros
  (:use clojure.tools.trace))

(defn validate [mapping]
  (let [[_ arrow _] mapping]
    (if-not (= (first arrow) '=>)
      (throw (Exception. "Mock mappings must be in form (fn arg arg arg... => result)"))
      mapping)))

(defn separate [mapping]
  (let [[fn-decl arrow result] mapping]
    [(first fn-decl) (rest fn-decl) (first result)]))

(defn partition-by-arrow [mapping]
  (partition-by #(= '=> %) mapping))

(defn convert [mapping]
  (->> mapping
       (partition-by-arrow)
       (validate)
       (separate)))

(defmacro provided [mock-mapping & body]
  (let [[first & rest] mock-mapping]
    (if first
      (let [[func args result] (convert first)]
        `(do
           (let [mock# (-> js/sinon (.mock ~func))]
               (try (provided ~rest ~body)
                    (litmus.assert/ok? (.called mock#))
                    (finally
                      (.restore mock#))))))
      `(do ~@body))))
