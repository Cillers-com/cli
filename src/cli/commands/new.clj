(ns cli.commands.new
  (:require [clojure.string :as str]
            [cli.return :as return]
            [cli.process :as proc]
            [cli.git :as git]
            [cli.file :as file]))

(defn check-args [args]
  (let [name (first args)
        err return/illegal-argument-error]
    (cond
      (empty? args) (err "No name provided")
      (> (count args) 1) (err "Command 'new' takes only one argument")
      (file/exists? name) (err (str "Directory " name " already exists"))
      :else (return/success))))

(defn check-dependencies [] 
  (let [err return/missing-dependency-error]
    (cond 
      (not (git/installed?)) (err "Git is not installed or not in the PATH.")
      :else (return/success))))

(defn command-fn [args options]
  (let [name (first args)
        verbose? (:verbose options)
        repo-url "https://github.com/Cillers-com/create-cillers-system"]
    (return/do-until-error
      [[check-args [args]]
       [check-dependencies []]
       [return/success-println [(str "Creating new system named '" name "'...")]]
       [git/clone [repo-url name verbose?]]
       [git/reset [name verbose?]]
       [return/success-println [(str "New Cillers system '" name "' successfully created.")]]])))

