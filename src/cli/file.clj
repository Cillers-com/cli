(ns cli.file
  (:require [clojure.java.io :as io]))

(defn exists? [name]
  (let [file (io/file name)]
    (.exists file)))

(defn assert-doesnt-exist [name]
  (assert (not (exists? name))))

(defn assert-exists [name]
  (assert (exists? name)))

