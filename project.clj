(defproject litmus "0.0.1"
  :description "Testing library for Clojurescript"
  :url "http://github.com/hsalokor/litmus"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :plugins [[lein-cljsbuild "0.3.0-1"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:test-commands {"unit" ["mocha-phantomjs" "resources/test.html"]}
              :builds {:dev {:source-paths ["src/cljs"]
                             :compiler {:output-to "target/litmus.js"
                                        :optimizations :whitespace
                                        :externs ["src/cljs/externs/mocha.js"
                                                  "src/cljs/externs/chai.js"]
                                        :pretty-print true}}
                       :prod {:source-paths ["src/cljs"]
			      :jar true
                              :compiler {:output-to "target/litmus.js"
                                         :optimizations :advanced
                                         :externs ["src/cljs/externs/mocha.js"
                                                   "src/cljs/externs/chai.js"]
                                         :pretty-print true}}

                       :test {:source-paths ["src/cljs"
                                             "test/cljs/"]
                              :compiler {:output-to "target/tests.js"
                                         :optimizations :whitespace
                                         :externs ["src/cljs/externs/mocha.js"
                                                   "src/cljs/externs/chai.js"]
                                         :pretty-print true}}}})
