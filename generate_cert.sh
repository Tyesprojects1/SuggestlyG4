#!/bin/sh
set -e
mkdir -p certs
openssl req -x509 -newkey rsa:2048 -days 365 -nodes \
  -keyout certs/server.key -out certs/server.crt \
  -subj "/CN=suggestify.local"
