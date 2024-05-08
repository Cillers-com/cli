(ns cli.core
  (:require [cli.return :as return]
            [cli.argv-parser :refer [parse-argv]]
            [cli.commands.version :refer [print-version]]
            [cli.commands.help :refer [print-help]]
            [cli.commands :refer [dispatch-command]])
  (:gen-class))

(defn handle-error [ret]
  (let [type (:type ret)
        message (:message ret)]
    (println "")
    (println (return/type-to-description type))
    (println message)
    (when (= type :illegal-argument-error) 
      (print-help))))

(defn -main [& args]
    (let [[command args options] (parse-argv (vec args))
          ret (dispatch-command command args options)]
      (when (return/error? ret)
        (handle-error ret)))
  (shutdown-agents))

