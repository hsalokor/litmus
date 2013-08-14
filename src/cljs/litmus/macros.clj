(ns litmus.macros)

;; Direct mapping of describe-it style
(defmacro describe [description & body]
  `(do (js/describe ~description
                    (fn [] ~@body))))

(defmacro describe! [done description & body]
  `(do (js/describe ~description
                    (fn [~done] ~@body))))

(defmacro it [description & body]
  `(do (js/it ~description
              (fn [] ~@body))))

(defmacro it! [done description & body]
  `(do (js/it ~description
              (fn [~done] ~@body))))

;; Aliases for it - given and then
(defmacro given [description & body]
  `(do (js/describe ~description
                    (fn [] ~@body))))

(defmacro given! [done description & body]
  `(do (js/describe ~description
                    (fn [~done] ~@body))))

(defmacro then [description & body]
  `(do (js/it ~description
              (fn [] ~@body))))

(defmacro then! [done description & body]
  `(do (js/it ~description
              (fn [~done] ~@body))))

;; Before and after
(defmacro before [& body]
  `(do (js/before (fn [] ~@body))))

(defmacro before! [done & body]
  `(do (js/before (fn [~done] ~@body))))

(defmacro after [& body]
  `(do (js/after (fn [] ~@body))))

(defmacro after! [done & body]
  `(do (js/after (fn [~done] ~@body))))

;; Before-each and after-each
(defmacro before-each [& body]
  `(do (js/beforeEach (fn [] ~@body))))

(defmacro before-each! [done & body]
  `(do (js/beforeEach (fn [~done] ~@body))))

(defmacro after-each [& body]
  `(do (js/afterEach (fn [] ~@body))))

(defmacro after-each! [done & body]
  `(do (js/afterEach (fn [~done] ~@body))))
