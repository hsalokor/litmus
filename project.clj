(defproject litmus "0.4.0"
  :description "Testing library for Clojurescript"
  :url "http://github.com/hsalokor/litmus"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :scm {:url "git@github.com/hsalokor/litmus.git"}
  :pom-addition [:developers [:developer
                              [:name "Harri Salokorpi"]
                              [:url "http://github.com/hsalokor/litmus"]
                              [:email "hsalokor@iki.fi"]
                              [:timezone "+3"]]]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [jayq "2.4.0"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:test-commands {"unit" ["mocha-phantomjs" "resources/test.html"]}
              :builds {:dev {:source-paths ["src/cljs"]
                             :compiler {:output-to "target/litmus.js"
                                        :optimizations :whitespace
                                        :externs ["src/cljs/externs/mocha.js"
                                                  "src/cljs/externs/chai.js"
                                                  "src/cljs/externs/jquery-1.9.js"]
                                        :pretty-print true}}
                       :prod {:source-paths ["src/cljs"]
			      :jar true
                              :compiler {:output-to "target/litmus.js"
                                         :optimizations :advanced
                                         :externs ["src/cljs/externs/mocha.js"
                                                   "src/cljs/externs/chai.js"
                                                   "src/cljs/externs/jquery-1.9.js"]
                                         :pretty-print true}}

                       :test {:source-paths ["src/cljs"
                                             "test/cljs/"]
                              :compiler {:output-to "target/tests.js"
                                         :optimizations :whitespace
                                         :externs ["src/cljs/externs/mocha.js"
                                                   "src/cljs/externs/chai.js"
                                                   "src/cljs/externs/jquery-1.9.js"]
                                         :pretty-print true}}}})
