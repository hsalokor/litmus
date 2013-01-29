(ns litmus.assert.syntax)

(defn check-arrow
  [arrow]
  (when-not (= "=>" arrow)
    (throw (js/Error. (str "Invalid syntax: asserts expect syntax in form "
                            "of (equals? actual => expected)")))))
