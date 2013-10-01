# Litmus

[![Build Status](https://travis-ci.org/hsalokor/litmus.png?branch=master)](https://travis-ci.org/hsalokor/litmus)

Simple Clojurescript bindings for Mocha.js, Chai.js, smoax and others.

# Usage

Add dependency

    [litmus "0.4.0]

To your project clj.

Litmus is self-testing - see `resources/` for test HTML, `project.clj`
for Leiningen setup and `test` for example tests.

# Features

* Basic test running with either `describe-it` style or `describe-given-then` style
* Asynchronous test support (via `before-each!`, `before!` etc.)
* AJAX mocking support via smoax (via `with-ajax-mocks`, `ajax-called?`, `ajax-called-with-json?` etc.)
* Simple DOM matchers (`does-exist?`, `is-visible?` etc.)

# Dependencies

For running tests

* Node.js
* Mocha-phantomjs
* jayq

For test HTML page

* Mocha.js
* Chai.js
* [Smoax](https://github.com/mtkopone/smoax)

# Initial setup

Update submodules

    git submodule init
    git submodule update

Install mocha-phantomjs

    npm install -g mocha
    npm install -g mocha-phantomjs

# Usage

Use leiningen

    lein test

Or, call mocha-phantomjs directly

    lein cljsbuild once
    mocha-phantomjs resources/test.html

# Contributors

* Harri Salokorpi (Author)
* Jori Ahvonen

# Backlog

* Helpers for conditionally running tests on (production) page. Url parameter http://...?test -> tests and deps are loaded - **WIP** -> topic/conditional_integration
* Integrate Sinon.js to provide mocks/spies - **WIP** -> topic/mocks
* Example project (for testing integration and as documentation)
* Add more assertions (see Chai Asserts module)
* Add support for async testing (macro-variants providing the done-callback)
* Compile using :foreign-libs, integrating the dependencies to test framework (less setup on new project). Licences?

## License

Copyright © 2013 Harri Salokorpi

Distributed under the Eclipse Public License, the same as Clojure.
