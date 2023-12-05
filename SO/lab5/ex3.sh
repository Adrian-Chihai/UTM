#!/bin/bash

# Directorul în care sunt localizate comenzile
director_comenzi="/sbin/"

# Numele fișierului în care vor fi stocate PID-urile
fisier_pid="pid_file.txt"

# Caută procesele din /sbin/ și obține PID-urile lor
piduri=$(
  for cmd in $director_comenzi*; do
    pid=$(pgrep -o "$(basename $cmd)")
    if [ -n "$pid" ]; then
      echo "$pid"
    fi
  done
)

# Salvează PID-urile în fișierul specificat
echo "$piduri" > "$fisier_pid"

# Afișează conținutul fișierului
cat "$fisier_pid"

