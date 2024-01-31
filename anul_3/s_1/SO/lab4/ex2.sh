#!/bin/bash

# Directorul de căutare
log_directory="/var/log"

# Calculăm numărul total de linii în fișierele cu extensia ".log"
total_lines=$(cat $log_directory/*.log 2>/dev/null | wc -l)

# Afișăm numărul total de linii
echo "Numărul total de linii în fișierele .log din $log_directory este: $total_lines"

