(ns litmus.assert.macros)

(defmacro throws? [body arrow exception regex]
  "Create necessary structure for checking exception throws without too much
   syntactic noise.

   Example: (throws? (throw (js/Error. \"error\")) => js/Error #\"error\")

   Note: the aget is required to bypass Google Closure compiler behaviour, where
   property with reserved keyword name causes compilation failure (in this case,
   'throws' or 'throw')."
  `(do (litmus.assert.syntax/check-arrow ~arrow)
       (apply (aget js/chai.assert "throws") [(fn [] ~body) ~exception ~regex])))
