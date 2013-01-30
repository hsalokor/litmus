(ns litmus.assert.macros)

(defmacro throws? [body arrow exception regex]
  "Create necessary structure for checking exception throws without too much
   syntactic noise.

   Note: the aget is required to bypass Google Closure compiler behaviour, where
   property with reserved keyword name causes compilation failure (in this case,
   'throws' or 'throw')."
  `(apply (aget js/chai.assert "throws") [(fn [] ~body) ~exception ~regex]))
