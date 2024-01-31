#!/bin/bash

while true; do
    # Afișează meniul
    echo "Meniu:"
    echo "1. Lansare nano"
    echo "2. Lansare vi"
    echo "3. Lansare Firefox"
    echo "4. Ieșire din meniu"

    # Citeste opțiunea de la tastatură
    read -p "Introdu numărul opțiunii: " optiune

    case $optiune in
        1)
            nano
            ;;
        2)
            vi
            ;;
        3)
            firefox
            ;;
        4)
            echo "Iesire din meniu."
            exit 0
            ;;
        *)
            echo "Opțiune invalidă. Introdu un număr între 1 și 4."
            ;;
    esac
done

