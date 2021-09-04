#!/bin/bash

source "/home/asw/shared/scripts/common.sh"

# see https://openjdk.java.net/install/ 

# set up Java constants 
# La versione 14 di OpenJDK non è presente nei repository APT dell'immagine
# di Ubuntu installata sulle macchine virtuali, quindi al suo posto utilizzo
# la versione 11.
# OPENJDK_PACKAGE=openjdk-8-jdk
# OPENJDK_PACKAGE=openjdk-14-jdk
OPENJDK_PACKAGE=openjdk-11-jdk

echo "==================="
echo "installing open jdk"
echo "==================="

apt-get update 
apt-get install -y ${OPENJDK_PACKAGE}
