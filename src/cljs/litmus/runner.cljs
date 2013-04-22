(ns litmus.runner)

(defn get-url-param [key]
  (let [pattern (str "[\\?&]" key "=([^&#]*)")
        url (.-URL js/document)]
    (-> (re-pattern pattern)
        (re-find url)
        (nth 1))))

(defn phantomjs? [] (.-mochaPhantomJS js/window))

(defn in-test-mode? [config]
  (= (get-url-param "test") "true"))

(defn ^export run-tests [js-config]
  (let [config (js->clj js-config)]
    (when (in-test-mode? config)
      (.log js/console "Litmus: starting tests")
      (if (phantomjs?)
        (.run js/mochaPhantomJS)
        (.run js/mocha)))))
