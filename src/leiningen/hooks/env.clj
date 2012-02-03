(ns leiningen.hooks.env
  (:require [robert.hooke :as hooke])
  (:use [clojure.string :only (split)]
        [leiningen.env.core :only (wrap-task)]
        clojure.tools.logging))

(def ^{:dynamic true} *hooks*
  '[leiningen.env/env
    leiningen.jack-in/jack-in
    leiningen.repl/repl
    leiningen.retest/retest
    leiningen.run/run
    leiningen.swank/swank
    leiningen.test!/test!
    leiningen.test/test
    leiningen.test/trampoline])

(defn add-hook [task]
  (let [[ns f] (map symbol (split (str task) #"/"))]
    (try
      (require ns)
      (hooke/add-hook (ns-resolve ns f) wrap-task)
      (catch Exception e
        (warn (format "Can't hook lein-env into %s: %s." task (.getMessage e)))))))

(doall (map add-hook *hooks*))
