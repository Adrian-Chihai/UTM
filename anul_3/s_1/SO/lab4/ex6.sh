#!/bin/bash

# Utilizează awk pentru a afișa numele utilizatorilor și UID-urile din /etc/passwd
awk -F':' '{print "Utilizator: " $1, "\tUID: " $3}' /etc/passwd | sort -t: -k3n

