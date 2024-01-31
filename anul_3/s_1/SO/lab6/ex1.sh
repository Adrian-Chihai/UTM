#!/bin/bash

# Crearea directorului "test" în directorul home
mkdir "$HOME/test2" 2>/dev/null

# Scrierea în fișierul raport în funcție de rezultatul creării directorul
if [ $? -eq 0 ]; then
    echo "catalog test was created successfully" > "$HOME/raport"
    touch "$HOME/test2/Data_Ora_Lansarii_Scriptului"
else
    echo "Error: catalog test creation failed" > "$HOME/raport"
fi
# Ping către www.traiasca_moldova.md și scrierea în fișierul raport în caz de eroare
ping -c 1 www.traiasca_moldova.md >/dev/null 2>&1 || echo "Error: www.traiasca_moldova.md is not reachable" >> "$HOME/raport"

