(defproject cli "0.0.6"
  :description "The Cillers CLI: For creation, devlopment and management of Cillers systems"
  :url "https://cillers.com"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot cli.core
  :target-path "target"
  :profiles {:uberjar {:aot :all
                       :dependencies [[com.github.clj-easy/graal-build-time "1.0.5"]]
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

