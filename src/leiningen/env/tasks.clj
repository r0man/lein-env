(ns leiningen.env.tasks
  (:use [clojure.pprint :only (pprint)]
        [clojure.string :only (join)]
        leiningen.env.core))

(defn list-environments
  "List all project environments."
  [project]
  (let [environments @*environments*]
    (if (empty? environments)
      (println "No environments found.")
      (println (format "Available environments: %s." (join ", " (sort (map name (keys environments)))))))))

(defn print-current-environment
  "Print the current project environment."
  [project] (println (str "Current environment: " (name *current*))))

(defn show-environment
  "Show the project environment by name."
  [project & [name]]
  (if-let [environment (get @*environments* (keyword name))]
    (do (pprint environment) (flush))
    (println (format "Environment '%s' not found" (str name)))))
