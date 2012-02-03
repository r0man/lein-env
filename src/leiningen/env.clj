(ns leiningen.env
  (:refer-clojure :exclude (list))
  (:use [leiningen.help :only (help-for)]
        leiningen.env.tasks))

(defn current
  "Print the current project environment."
  [project] (print-current-environment project))

(defn list
  "List all project environments."
  [project] (list-environments project))

(defn show
  "Show the project environment by name."
  [project & [name]] (show-environment project name))

(defn env
  "Leiningen project environments."
  {:help-arglists '([current list show])
   :subtasks [#'current #'list #'show]}
  ([project]
     (env project "current"))
  ([project subtask & args]
     (cond
      (= "help" subtask) (println (help-for "env"))
      (= "list" subtask) (apply list project args)
      (= "show" subtask) (apply show project args)
      :else (current project))))
