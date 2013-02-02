(ns litmus.mocks
  (:use [litmus.assert :only [ok?]]))

(defn setup [mapping]
  (let [[call arrow result] mapping
        stub (-> (.stub js/sinon)
                 (.returns result))]
    [stub mapping]))

(defn verify [mock]
  (let [[stub _] mock]
    (ok? (.called stub))))

(defn restore [mock]
  (let [[stub _] mock]
    (.restore stub)))
  
