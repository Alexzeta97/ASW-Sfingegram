#!/bin/bash

# Script per avviare l'applicazione SfingeGram con più repliche di alcuni servizi.

echo Running SFINGEGRAM with multiple service istances

# Utilizzo due repliche per i servizi "enigmi" e "connessioni" e tre repliche per
# il servizio "enigmi-seguiti".
docker-compose up --scale enigmi=2 --scale connessioni=2 --scale enigmiseguiti=3
