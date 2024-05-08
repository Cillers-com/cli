(ns cli.git 
  (:require [clojure.string :as str]
            [cli.return :as return]
            [cli.process :as process]
            [cli.file :as file]))

(defn installed? []
  (let [ret (process/execute "." ["git" "version"] false)]
    (and (return/success? ret) (not (empty? (:value ret))))))

(defn assert-origin-url [path url] 
  (let [argv ["git" "remote" "get-url" "origin"]
        ret (process/execute path argv false)]
    (return/assert-success ret)
    (assert (= (:value ret) url))))

(defn assert-exactly-one-commit [path]
  (let [argv ["git" "rev-list" "--count" "HEAD"]
        ret (process/execute path argv false)]
    (return/assert-success ret)
    (assert (= (str/trim (:value ret)) "1")))) 

(defn assert-is-reset [path]
  (let [argv ["git" "log" "--format=%B" "-n" "1"]
        ret (process/execute path argv false)]
    (return/assert-success ret)
    (assert (= (str/trim (:value ret)) "'Initial commit'"))
    (assert-exactly-one-commit path)))

(defn clone [url target-name verbose?]
  (do 
    (file/assert-doesnt-exist target-name)
    (let [argv ["git" "clone" url "--progress" target-name]
          ret (process/execute "." argv verbose?)]
      (if (return/error? ret)
        ret
        (do
          (file/assert-exists target-name)
          (assert-origin-url target-name url)
          (return/success))))))

(defn reset [path verbose?]
  (do
    (file/assert-exists path)
    (let [ret (return/do-until-error
                [[process/execute [path ["rm" "-rf" ".git"] verbose?]]
                 [process/execute [path ["git" "init"] verbose?]]
                 [process/execute [path ["git" "add" "."] verbose?]]
                 [process/execute [path ["git" "commit" "-m" "'Initial commit'"] verbose?]]])]
      (if (return/error? ret)
        ret
        (do
          (assert-is-reset path)
          (return/success))))))

