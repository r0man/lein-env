(ns leiningen.env
  (:refer-clojure :exclude (list))
  (:use [clojure.pprint :only (pprint)]
        [clojure.string :only (join)]
        [leiningen.help :only (help-for)]
        [leiningen.env.core :only (read-environments)]))

(defn list
  "List all project environments."
  [project]
  (let [environments (read-environments project)]
    (if (empty? environments)
      (println "No environments found.")
      (println (format "Environments: %s." (join ", " (map name (keys environments))))))))

(defn show
  "Show the project environment by name."
  [project & [name]]
  (if-let [environment (get (read-environments project) (keyword name))]
    (do (pprint environment) (flush))
    (println (format "Environment '%s' not found" (str name)))))

(defn env
  "Leiningen project environments."
  {:help-arglists '([list show])
   :subtasks [#'list #'show]}
  ([project]
     (println (help-for "env")))
  ([project subtask & args]
     (case subtask
       "list" (apply list project args)
       "show" (apply show project args))))
