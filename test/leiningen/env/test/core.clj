(ns leiningen.env.test.core
  (:use clojure.test
        leiningen.env.core))

(def project {:name "example"})

(deftest test-current-environment
  (load-environments project "test-resources/init.clj")
  (is (= {:hostname "localhost"} (current-environment)))
  (with-environment :development
    (is (= {:hostname "localhost"} (current-environment))))
  (with-environment :test
    (is (= {:hostname "example.com"} (current-environment)))))

(deftest test-load-environments
  (swap! *environments* (constantly {}))
  (is (= {} (load-environments project "NOT-EXISTING")))
  (let [environments (load-environments project "test-resources/init.clj")]
    (is (= 2 (count environments)))
    (is (= {:hostname "localhost"} (:development environments)))
    (is (= {:hostname "example.com"} (:test environments)))))

(deftest test-read-environments
  (is (nil? (read-environments project "NOT-EXISTING")))
  (let [environments (read-environments project "test-resources/init.clj")]
    (is (= 2 (count environments)))
    (is (= {:hostname "localhost"} (:development environments)))
    (is (= {:hostname "example.com"} (:test environments)))))

(deftest test-project-symbol
  (is (= 'example-environments (project-symbol project))))
