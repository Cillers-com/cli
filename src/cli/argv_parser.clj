(ns cli.argv-parser)

(def supported-options
  #{:verbose})

(def supported-commands
  #{:new :version :help})

(defn parse-option-argv [option-argv]
  (reduce (fn [acc option]
            (let [option-key (keyword (.substring option 2))]
              (if (contains? supported-options option-key)
                (assoc acc option-key true)
                (throw (IllegalArgumentException. (str "Unsupported option: " option))))))
          {}
          option-argv))

(defn option? [arg]
  (clojure.string/starts-with? arg "-"))

(defn index-of-first-option [argv]
  (let [num-args (count argv)]
    (loop [idx 0]
      (assert (<= idx num-args)) 

      (cond
        (= idx num-args) num-args
        (option? (nth argv idx)) idx
        :else (recur (inc idx))))))

(defn parse-command-argv [command-argv]
  (let [command (keyword (first command-argv))
        args (vec (rest command-argv))]
  
    (when-not (first command-argv)
      (throw (IllegalArgumentException. "No command provided")))

    (when-not (contains? supported-commands command)
      (throw (IllegalArgumentException. (str "Unsupported command: " command))))

    [command args]))  

(defn parse-argv [argv]
  ;; Allow help and version to be specified as options because that is common practice, but treat 
  ;; them as commands because that is more true to their semantics. 
  (let [has-help-option (> (.indexOf argv "--help") -1)
        has-version-option (> (.indexOf argv "--version") -1)]
    (cond 
      has-help-option [:help [] {}]
      has-version-option [:version [] {}]
      :else
      (let [idx (index-of-first-option argv)
            command-argv (take idx argv)
            option-argv (drop idx argv)
            [command args] (parse-command-argv command-argv)
            options (parse-option-argv option-argv)]
        [command args options]))))

