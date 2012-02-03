(ns leiningen.env.test.tasks
  (:use clojure.test
        leiningen.env.core
        leiningen.env.tasks))

(def project {:name "lein-env"})

(deftest test-list-environments
  (let [output (with-out-str (list-environments project))]
    (is (= "Available environments: development, test.\n" output))))

(deftest test-print-current-environment
  (let [output (with-out-str (print-current-environment project))]
    (is (= "Current environment: development\n" output))))

(deftest test-show-environment
  (let [output (with-out-str (show-environment project "test"))]
    (is (= "{:hostname \"example.com\"}\n" output))))

(set-environments! project "test-resources/init.clj")
