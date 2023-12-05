#!/bin/bash

# Numele fișierului în care vor fi stocate rezultatele
fisier_rezultate="diferente_memorie.txt"

# Obține PID-urile proceselor din /sbin/
piduri=$(
  for cmd in /sbin/*; do
    pid=$(pgrep -o "$(basename $cmd)")
    if [ -n "$pid" ]; then
      echo "$pid"
    fi
  done
)

# Parcurge fiecare PID și calculează diferența între memoria totală și memoria rezidentă
for pid in $piduri; do
  # Obține informații despre memoria procesului
  size=$(awk '/VmSize/{print $2}' "/proc/$pid/status")
  resident=$(awk '/VmRSS/{print $2}' "/proc/$pid/status")

  # Calculează diferența
  diferenta=$((size - resident))

  # Introduce în fișier linia "PID:diferenta"
  echo "$pid:$diferenta" >> "$fisier_rezultate"
done

# Sortează fișierul în ordinea descrescătoare a diferențelor
sort -t':' -k2,2nr -o "$fisier_rezultate" "$fisier_rezultate"

# Afișează conținutul fișierului
cat "$fisier_rezultate"

