#!/usr/bin/env bash
curl https://github.com/downloads/technomancy/leiningen/leiningen-1.6.2-standalone.jar > /tmp/leiningen-1.6.2.jar
mvn install:install-file -DgroupId=leiningen -DartifactId=leiningen -Dversion=1.6.2 -Dpackaging=jar -Dfile=/tmp/leiningen-1.6.2.jar
