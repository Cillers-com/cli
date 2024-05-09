# Cillers CLI 

Read more about the Cillers CLI at https://docs.cillers.com/

## Build command
native-image \
  --no-fallback \
  -jar target/uberjar/cli-0.0.4-standalone.jar \
  -H:Name=cillers-cli-amd64 \
  -H:ReflectionConfigurationFiles=reflection.json \
  -H:ResourceConfigurationFiles=resource-config.json \
  -H:+UnlockExperimentalVMOptions \
  -H:+ReportExceptionStackTraces \
  --report-unsupported-elements-at-runtime \
  --initialize-at-build-time \
  -Dclojure.compiler.direct-linking=true

