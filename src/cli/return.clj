(ns cli.return)

(defn success
  ([] 
    {:code :success})
  ([value] 
    {:code :success
     :value value}))

(defn error [type, message]
  {:code :error 
   :type type 
   :message message})

(def error-types
  {:illegal-argument-error "The provided arguments are not valid." 
   :missing-dependency-error "A dependency is missing."
   :misconfiguration "Something is wrong with the configuration."
   :process-execution-error "There was an error when executing a process."}) 

(defn type-to-description [type]
  (get error-types type))

(defn illegal-argument-error [message]
  (error :illegal-argument-error message))

(defn missing-dependency-error [message]
  (error :missing-dependency-error message))

(defn misconfiguration-error [message]
  (error :misconfiguration-error message))

(defn process-execution-error [message]
  (error :process-execution-error message))

(defn success? [ret]
  (= (:code ret) :success))

(defn error? [ret]
  (= (:code ret) :error))

(defn assert-success [ret]
  (assert (success? ret)))

(defn do-until-error [fn-args-pairs]
  (reduce (fn [acc [f args]]
            (if (error? acc)
              (reduced acc)
              (apply f args)))
          {}  
          fn-args-pairs))

(defn success-println [message]
  (do
    (println message)
    (success)))

