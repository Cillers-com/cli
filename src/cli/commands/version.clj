(ns cli.commands.version
  (:require [clojure.java.io :as io]
            [cli.return :as return]))

(defn get-version []
    "v0.0.10")

;;(let [project-file (slurp (io/file "project.clj"))
;;        version-matcher #"defproject\s+\S+\s+\"([^\"]+)\""
;;        matches (re-find version-matcher project-file)]
;;    (assert (and matches (second matches)))
;;    (return/success (second matches))))

(defn print-version [] 
  (let [ret (get-version)]
    (if (return/error? ret)
      ret
      (let [version (:value ret)] 
        (return/success-println (str "\nCillers CLI version: " version "\n"))))))

(defn command-fn [args options]
  (print-version))
