(ns leiningen.env.core
  (:refer-clojure :exclude (replace))
  (:use [clojure.java.io :only (file)]
        [clojure.string :only (upper-case replace split)]
        clojure.tools.logging))

(def ^{:dynamic true} *default* :development)
(def ^{:dynamic true} *current* *default*)
(def ^{:dynamic true} *environments* (atom {}))

(def user-init-path
  (str (file (System/getenv "HOME") ".lein" "init.clj")))

(defn environment
  "Returns the current or the given environment."
  [& [env]] (get @*environments* (or env *current*)))

(defn variable-name
  "Returns the environment variable name of the project."
  [project]
  (if-let [name (:name project)]
    (-> name (replace "-" "_") upper-case (str "_ENV"))))

(defn project-env
  "Returns the project's environment as a keyword."
  [project]
  (if-let [name (variable-name project)]
    (keyword (or (System/getenv name) *default*))
    *default*))

(defn resolve-environments
  "Resolve the project environments in the 'user or the given namespace."
  [project & [ns]]
  (let [ns (or ns 'user)
        name (last (split (str (:name project)) #"/"))
        environments (ns-resolve ns (symbol name))]
    (if environments
      (do (debug (format "Project environments #'%s/%s successfully loaded." ns (:name project)))
          @environments))))

(defn read-environments
  "Read the environments for project."
  [project & [path ns]]
  (let [file (file (or path user-init-path))]
    (when (.exists file)
      (debug (format "Loading environments from %s." file))
      (in-ns (or ns 'user))
      (load-file (str file))
      (resolve-environments project ns))))

(defn set-environments!
  "Load the environments for project."
  [project & [path ns]]
  (alter-var-root (var *current*) (constantly (project-env project)))
  (swap! *environments* merge (:environments project) (read-environments project path ns)))

(defn wrap-task [task & [project & args]]
  (set-environments! project)
  (apply task project args))

(defmacro with-environment
  "Evaluate body with *current* bound to the environment name."
  [name & body] `(binding [*current* ~(keyword name)] ~@body))
