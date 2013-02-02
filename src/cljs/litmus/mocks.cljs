(ns litmus.mocks
  (:use [litmus.assert :only [ok?]]))

(defn setup [mapping]
  (let [[func _ result] mapping
        stub (-> (.stub js/sinon func)
                 (.returns result))]
    [stub mapping]))

(defn verify [mock]
  (let [[stub _] mock]
    (ok? (.called stub))))

(defn restore [mock]
  (let [[stub _] mock]
    (.restore stub)))
  
