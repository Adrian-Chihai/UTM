#!/bin/bash

# Functie pentru calcul ciclic infinit
calculate_infinite_loop() {
    while true; do
        id 
    done
}

# Lansarea primului proces în fundal cu limitare de utilizare a CPU
cpulimit -l 20 -- pidof -x "$(basename "$0")"
#calculate_infinite_loop &

# Lansarea celui de-al doilea proces în fundal fără limitare de utilizare a CPU
#bash -c  "calculate_infinite_loop()" &

# Așteptarea ca procesele să ruleze
#wait

