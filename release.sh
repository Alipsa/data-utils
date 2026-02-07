#!/usr/bin/env bash
set -e
if [[ $(git status --porcelain) ]]; then
  echo "Git changes detected, commit all changes first before releasing"
  exit 1
fi
if [[ -f ~/.sdkman/bin/sdkman-init.sh ]]; then
  source ~/.sdkman/bin/sdkman-init.sh
else
  echo "SDKMAN is not installed, please install it first to manage Java versions"
  exit 1
fi

if command -v jdk21 >/dev/null 2>&1; then
  source jdk21
else
  javaVersion=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | sed 's/^1\.//;s/\..*//')
  if (( javaVersion == 21 )) ; then
    echo "java 21 is already the active version"
  else
      echo "Java 21 is not installed or not configured in SDKMAN, please install it first"
      exit 1
  fi
fi
./gradlew clean release