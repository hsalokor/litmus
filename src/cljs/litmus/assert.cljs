(ns litmus.assert)

(def => "=>")

(defn- check-arrow
  [arrow]
  (when-not (= => arrow)
    (throw (js/Error. (str "Invalid syntax: asserts expect syntax in form "
                            "of (equals? actual => expected)")))))

(defn equals?
  [expected arrow actual]
  (check-arrow arrow)
  (.equal chai.assert expected actual))
