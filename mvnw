#!/bin/sh
# Minimal mvnw script using downloaded maven or system maven
DIR="$(cd "$(dirname "$0")" && pwd)"
if [ -d "$DIR/apache-maven-3.9.9" ]; then
    exec "$DIR/apache-maven-3.9.9/bin/mvn" "$@"
elif command -v mvn >/dev/null 2>&1; then
    exec mvn "$@"
else
    echo "Maven not found. Please install Maven or extract apache-maven to project root."
    exit 1
fi
