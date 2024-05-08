(ns cli.argv-parser
  (:require [cli.return :as return]))

(def supported-options
  #{:verbose})

(def supported-commands
  #{:new :version :help})

(defn supported-option? [option]
  (contains? supported-options option))

(defn supported-command? [command]
  (contains? supported-commands command))

(defn option? [arg]
  (clojure.string/starts-with? arg "-"))

(defn parse-option-argv [option-argv]
  (reduce (fn [acc option]
            (let [option-key (keyword (.substring option 2))]
              (if (not (supported-option? option-key))
                (reduced (return/error (str "Unsupported option: " option)))
                (return/success (assoc (:value acc) option-key true)))))
          (return/success {})
          option-argv))

(defn parse-command-argv [command-argv]
  (let [command (keyword (first command-argv))
        args (vec (rest command-argv))]
    (cond  
      (not (first command-argv)) (return/error "No command provided")  
      (not (supported-command? command)) (return/error (str "Unsupported command: " command))
      :else (return/success {:command command :args args}))))  

(defn index-of-first-option [argv]
  (let [num-args (count argv)]
    (loop [idx 0]
      (assert (<= idx num-args)) 
      (cond
        (= idx num-args) num-args
        (option? (nth argv idx)) idx
        :else (recur (inc idx))))))

(defn parse-argv [argv]
  ;; Allow help and version to be specified as options because that is common practice, but treat 
  ;; them as commands because that is more true to their semantics. 
  (let [has-help-option (> (.indexOf argv "--help") -1)
        has-version-option (> (.indexOf argv "--version") -1)]
    (cond 
      has-help-option (return/success [:command :help :args [] :options {}])
      has-version-option (return/success [:command :version :args [] :options {}])
      :else
      (let [idx (index-of-first-option argv)
            command-argv (take idx argv)
            option-argv (drop idx argv)
            ret-parse-command-argv (parse-command-argv command-argv)
            ret-parse-option-argv (parse-option-argv option-argv)]
        (cond
          (return/error? ret-parse-command-argv) ret-parse-command-argv
          (return/error? ret-parse-option-argv) ret-parse-option-argv
          :else 
          (let [command (:command (:value ret-parse-command-argv))
                args (:args (:value ret-parse-command-argv))
                options (:value ret-parse-option-argv)]
            (assert command)
            (assert args)
            (assert options)
            (return/success {:command command :args args :options options})))))))

