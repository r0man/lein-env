(defproject lein-env/lein-env "0.0.3-SNAPSHOT"
  :description "Leiningen project environments."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [robert/hooke "1.1.2"]]
  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[leiningen/leiningen "2.0.0-preview2"]]
                   :resource-paths ["test-resources"]}}
  :environments {:development
                 {:api-key "LEIN-PROJECT-DEVELOPMENT-API-KEY"},
                 :test {:api-key "LEIN-PROJECT-TEST-API-KEY"}})
