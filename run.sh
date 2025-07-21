#!/bin/sh
set -e
javac -Xlint:all Vault.java VaultServer.java VaultApplication.java
java VaultApplication
