# Litmus

Simple Clojurescript bindings for Mocha.js, Sinon.js and others.

# Dependencies

For running tests

* Node.js
* Mocha-phantomjs (for running tests)

For test HTML page

* Mocha.js

## Initial setup

Update submodules

    git submodule init
    git submodule update

Install mocha-phantomjs

    npm install -g mocha
    npm install -g mocha-phantomjs

## Usage

   lein cljsbuild once
   mocha-phantomjs resources/test.html

## License

Copyright © 2013 Harri Salokorpi

Distributed under the Eclipse Public License, the same as Clojure.
