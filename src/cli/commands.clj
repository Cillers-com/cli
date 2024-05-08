(ns cli.commands
  (:require [cli.commands.version :as version]
            [cli.commands.help :as help]
            [cli.commands.new :as new]))

(defn get-command-fn [command]
  (let [command-ns (str "cli.commands." (name command))
        command-ns-symbol (symbol command-ns)
        ns-object (find-ns command-ns-symbol)
        command-fn-symbol (symbol (str command-ns "/command-fn"))]     
    (and ns-object (ns-resolve ns-object command-fn-symbol))))

(defn dispatch-command [command args options]
  (let [command-fn (get-command-fn command)]
    (assert command-fn (str "Command function not found: " (name command)))
    (command-fn args options)))

