#!/bin/sh
set -e
javac -Xlint:all Vault.java VaultApplication.java
java VaultApplication
