(defproject lein-env/lein-env "0.0.4-SNAPSHOT"
  :description "Leiningen project environments."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [robert/hooke "1.1.2"]]
  :min-lein-version "2.0.0"
  :profiles {:dev {:resource-paths ["test-resources"]}}
  :eval-in :leiningen
  :environments {:development
                 {:api-key "LEIN-PROJECT-DEVELOPMENT-API-KEY"},
                 :test {:api-key "LEIN-PROJECT-TEST-API-KEY"}})
