(ns litmus.macros)

; Direct mapping of describe-it style
(defmacro describe [description & body]
  `(do (js/describe ~description
                    (fn [] ~@body))))

(defmacro it [description & body]
  `(do (js/it ~description
              (fn [] ~@body))))

; Aliases for it - given and then
(defmacro given [description & body]
  `(do (js/describe ~description
                    (fn [] ~@body))))

(defmacro then [description & body]
  `(do (js/it ~description
              (fn [] ~@body))))
