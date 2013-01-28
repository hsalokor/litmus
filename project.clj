(defproject litmus "0.1.0-SNAPSHOT"
  :description "Testing library for Clojurescript"
  :url "http://github.com/hsalokor/litmus"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :plugins [[lein-cljsbuild "0.3.0"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:output-to "target/litmus.js"
                                   :optimizations :whitespace
                                   :externs ["src/cljs/externs/mocha.js"]
                                   :pretty-print true}}
                       {:source-paths ["src/cljs"
                                       "test/cljs/"]
                        :compiler {:output-to "target/tests.js"
                                   :optimizations :whitespace
                                   :externs ["src/cljs/externs/mocha.js"]
                                   :pretty-print true}}]})
