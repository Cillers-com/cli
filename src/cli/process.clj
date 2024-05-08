(ns cli.process 
  (:require [clojure.string :as str]
            [cli.return :as return])
  (:import [java.io File]
           [java.io BufferedReader InputStreamReader]
           [java.lang ProcessBuilder]))

(defn execute [path command-argv verbose?]
  (let [dir (File. path)
        absolute-path (str (.getAbsolutePath dir)) 
        process-builder (ProcessBuilder. (into-array String command-argv))]
    (.directory process-builder dir) 
    (when verbose?
      (.inheritIO process-builder)  
      (println (str "Executing in " absolute-path ": " (str/join " " command-argv))))
    (let [process (.start process-builder)
          out-reader (BufferedReader. (InputStreamReader. (.getInputStream process)))
          err-reader (BufferedReader. (InputStreamReader. (.getErrorStream process)))]
      (let [exit-status (.waitFor process)
            output (str/join "\n" (line-seq out-reader))
            errors (str/join "\n" (line-seq err-reader))]
        (when verbose?
          (println "Exit status:" exit-status))
        (if (not (zero? exit-status))
          (return/process-execution-error errors)
          (return/success output))))))

