#!/usr/bin/env bash
if [[ $(git status --porcelain) ]]; then
  echo "Git changes detected, commit all changes first before releasing"
  exit
fi
source ~/.sdkman/bin/sdkman-init.sh
source jdk21
./gradlew clean release