(ns litmus.macros)

;; Direct mapping of describe-it style
(defmacro describe [description & body]
  `(do (js/describe ~description
                    (fn [] ~@body))))

(defmacro it [description & body]
  `(do (js/it ~description
              (fn [] ~@body))))

;; Aliases for it - given and then
(defmacro given [description & body]
  `(do (js/describe ~description
                    (fn [] ~@body))))

(defmacro then [description & body]
  `(do (js/it ~description
              (fn [] ~@body))))

;; Before and after
(defmacro before [& body]
  `(do (js/before (fn [] ~@body))))

(defmacro after [& body]
  `(do (js/after (fn [] ~@body))))

;; Before-each and after-each
(defmacro before-each [& body]
  `(do (js/beforeEach (fn [] ~@body))))

(defmacro after-each [& body]
  `(do (js/afterEach (fn [] ~@body))))
