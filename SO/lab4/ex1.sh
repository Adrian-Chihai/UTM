#!/bin/bash

# Numele fișierului în care vom salva liniile cu secvența "ACPI"
log_file="errors.log"

# Directorul de căutare
log_directory="/var/log"

# Căutăm liniile care conțin secvența "ACPI" în toate fișierele accesibile pentru citire
grep -r "ACPI" $log_directory/* 2>/dev/null > $log_file

# Afișăm doar numele fișierelor, excluzând calea
echo -e "\nLiniile din $log_file care conțin numele fișierelor (excluzând calea):"
awk -F':' '{print $1}' $log_file

# Afișăm conținutul complet al fișierului
echo -e "\nConținutul fișierului $log_file:"
cat $log_file

