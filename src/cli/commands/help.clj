(ns cli.commands.help
  (:require [clojure.string :as str]
            [cli.return :as return]
            [cli.commands.version :as version]))

(def help-text "
Usage: cillers [command] [options]

Commands:
  new <name>        Create a new system with the specified name.
  help              Show this help message.
  version           Show the version number.

Options:
  --verbose        Enable verbose output for debugging purposes.
  ")

(defn print-help []
  (let [ret (version/get-version)]
    (if (return/error? ret)
      ret
      (let [version (:value ret)]
        (return/success-println (str "\nCillers CLI version: " version "\n" help-text))))))

(defn command-fn [args options]
  (print-help))

