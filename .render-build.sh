#!/bin/bash
# Instala Java 17
sudo apt-get update
sudo apt-get install -y wget
wget https://packages.microsoft.com/config/ubuntu/20.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
sudo dpkg -i packages-microsoft-prod.deb
sudo apt-get update
sudo apt-get install -y temurin-17-jdk

# Configura variáveis
export JAVA_HOME=/usr/lib/jvm/temurin-17-jdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Build do Maven
chmod +x mvnw
./mvnw clean install