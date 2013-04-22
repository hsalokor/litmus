# Litmus

[![Build Status](https://travis-ci.org/hsalokor/litmus.png?branch=master)](https://travis-ci.org/hsalokor/litmus)

Simple Clojurescript bindings for Mocha.js, Sinon.js and others.

# Dependencies

For running tests

* Node.js
* Mocha-phantomjs (for running tests)

For test HTML page

* Mocha.js
* Chai.js

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

# Backlog

* Helpers for conditionally running tests on (production) page. Url parameter http://...?test -> tests and deps are loaded
* Integrate Sinon.js to provide mocks/spies - **WIP** -> topic/mocks
* Example project (for testing integration and as documentation)
* Add more assertions (see Chai Asserts module)
* Add support for async testing (macro-variants providing the done-callback)
* Compile using :foreign-libs, integrating the dependencies to test framework (less setup on new project). Licences?

## License

Copyright © 2013 Harri Salokorpi

Distributed under the Eclipse Public License, the same as Clojure.
