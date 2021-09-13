#!/bin/bash

# Script per costuire le immagini dei container Docker che compongono l'applicazione.
# Questo script va eseguito dopo aver compilato tutto il codice sorgente Java.

echo 'Building Docker images'
docker-compose build
