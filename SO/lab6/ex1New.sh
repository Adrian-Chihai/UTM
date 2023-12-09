#!/bin/bash

# Crearea directorului "test" în directorul home
mkdir ~/test 2>/dev/null

# Verificarea rezultatului și scrierea în fișierul raport
if [ $? -eq 0 ]; then
    echo "catalog test was created successfully" > ~/raport
    touch ~/test/Data_Ora_Lansarii_Scriptului
else
    echo "Error: catalog test creation failed" > ~/raport
fi

# Ping către www.traiasca_moldova.md și scrierea în fișierul raport
ping -c 1 www.traiasca_moldova.md >/dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "Error: www.traiasca_moldova.md is not reachable" >> ~/raport
fi

#echo "bash ~/calea-catre-script/script.sh" | at now + 2 minutes

# Verificarea pentru a rula doar în zilele pare
day_of_week=$(date +%u)
if [ $((day_of_week % 2)) -eq 0 ]; then
    # Adăugarea script-ului pentru a fi executat la fiecare 5 minute
    echo "*/5 * * * * bash ~/calea-catre-script/script.sh" | crontab
fi
