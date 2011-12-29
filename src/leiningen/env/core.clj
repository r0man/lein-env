(ns leiningen.env.core
  (:use [clojure.java.io :only (file)]))

(def ^:dynamic *current* :development)
(def ^:dynamic *environments* (atom {}))

(def lein-init-path (str (file (System/getenv "HOME") ".lein" "init.clj")))

(defn current-environment
  "Returns the current environment."
  [] (get @*environments* *current*))

(defn project-symbol
  "Returns the symbol under which the environments are stored."
  [project] (symbol (str (:name project) "-environments" )))

(defn resolve-environments
  "Resolve the project environments in the 'user or the given namespace."
  [project & [ns]]
  (if-let [environments (ns-resolve (or ns 'user) (project-symbol project))]
    @environments))

(defn read-environments
  "Read the environments for project."
  [project & [path ns]]
  (let [file (file (or path lein-init-path))]
    (when (.exists file)
      (in-ns (or ns 'user))
      (load-file (str file))
      (resolve-environments project ns))))

(defn load-environments
  "Load the environments for project."
  [project & [path ns]]
  (swap! *environments* merge (read-environments project path ns)))

(defmacro with-environment
  "Evaluate body with *current* bound to the environment name."
  [name & body] `(binding [*current* ~(keyword name)] ~@body))
