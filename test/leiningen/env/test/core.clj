(ns leiningen.env.test.core
  (:use clojure.test
        leiningen.env.core))

(def project {:name "lein-env"})

(deftest test-variable-name
  (is (nil? (variable-name nil)))
  (is (nil? (variable-name {})))
  (is (= "LEIN_ENV" (variable-name project))))

(deftest test-project-env
  (is (= :development (project-env nil)))
  (is (= :development (project-env {})))
  (is (= :development (project-env project))))

(deftest test-environment
  (set-environments! project "test-resources/init.clj")
  (is (= {:hostname "localhost"} (environment)))
  (is (= {:hostname "example.com"} (environment :test)))
  (with-environment :development
    (is (= {:hostname "localhost"} (environment))))
  (with-environment :test
    (is (= {:hostname "example.com"} (environment)))))

(deftest test-set-environments!
  (swap! *environments* (constantly {}))
  (is (= {} (set-environments! project "NOT-EXISTING")))
  (let [environments (set-environments! project "test-resources/init.clj")]
    (is (= 2 (count environments)))
    (is (= {:hostname "localhost"} (:development environments)))
    (is (= {:hostname "example.com"} (:test environments)))))

(deftest test-read-environments
  (is (nil? (read-environments project "NOT-EXISTING")))
  (let [environments (read-environments project "test-resources/init.clj")]
    (is (= 2 (count environments)))
    (is (= {:hostname "localhost"} (:development environments)))
    (is (= {:hostname "example.com"} (:test environments)))))
