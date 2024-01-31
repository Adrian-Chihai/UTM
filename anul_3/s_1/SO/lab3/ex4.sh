#!/bin/bash

numar_cifre=0

while true; do
    read -n 1 nr

    if [ $((nr % 2)) -eq 0 ]; then
        break
    fi

    # Verifică dacă caracterul este o cifra cu ajutorul expresiilor regulate
    if [[ "$nr" =~ ^[0-9]$ ]]; then
        ((numar_cifre++))
    fi
done

echo -e "\nAi introdus $numar_cifre ciffre"

