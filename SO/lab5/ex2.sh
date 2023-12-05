#!/bin/bash

# Obține informații despre ultimul proces lansat
ultimul_proces=$(ps -eo pid,lstart --sort=start_time | tail -n 1)

# Extrage PID-ul și timpul de lansare utilizând awk
pid=$(echo $ultimul_proces | awk '{print $1}')
timp_lansare=$(echo $ultimul_proces | awk '{$1=""; print $0}' | sed 's/^[ \t]*//')

# Afișează rezultatele
echo "PID-ul ultimului proces: $pid"
echo "Timpul de lansare: $timp_lansare"

