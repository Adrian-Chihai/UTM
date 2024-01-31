#!/bin/bash

if [ "$PWD" == "$HOME" ]; then
    echo "Directorul curent este directorul Home: $HOME"
    exit 0
else
    echo "Eroare: Scriptul trebuie să fie lansat din directorul Home."
    exit 1
fi

