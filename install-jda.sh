#!/bin/bash

set -xe

version="5.0.1"
downloadUrl="https://github.com/discord-jda/JDA/releases/download/v${version}/JDA-${version}-withDependencies-min.jar"
file="target/jda.jar"

mkdir -p target/

curl -L $downloadUrl -o $file

mvn install:install-file -Dfile="$file" -DgroupId="net.dv8tion" -DartifactId="JDA" -Dversion="${version}-min" -Dpackaging=jar

echo "Installed version ${version}"
