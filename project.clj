(defproject lein-env "0.0.2-SNAPSHOT"
  :description "Leiningen project environments."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [robert/hooke "1.1.2"]]
  :dev-dependencies [[leiningen/leiningen "1.6.2"]]
  :environments {:development {:api-key "LEIN-PROJECT-DEVELOPMENT-API-KEY"}
                 :test {:api-key "LEIN-PROJECT-TEST-API-KEY"}}
  :hooks [leiningen.hooks.env])
