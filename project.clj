(defproject cli "0.0.4"
  :description "The Cillers CLI: For creation, devlopment and management of Cillers systems"
  :url "https://cillers.com"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot cli.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
