# Litmus

Simple Clojurescript bindings for Mocha.js, Sinon.js and others.

# Dependencies

For running tests

* Node.js
* Mocha-phantomjs (for running tests)

For test HTML page

* Mocha.js

# Initial setup

Update submodules

    git submodule init
    git submodule update

Install mocha-phantomjs

    npm install -g mocha
    npm install -g mocha-phantomjs

# Usage

    lein cljsbuild once
    mocha-phantomjs resources/test.html

# Backlog

* Self-testing (needs at minimum throws? assertion)
* Integrate tests to lein cljsbuild
* Integrate Sinon.js to provide mocks/spies
* Compile using :foreign-libs, integrating the dependencies to test framework (less setup on new project). Licences?

## License

Copyright © 2013 Harri Salokorpi

Distributed under the Eclipse Public License, the same as Clojure.
