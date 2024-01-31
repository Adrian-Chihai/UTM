#!/bin/bash

# Găsește și extrage adresele de poștă electronică din fișierele din /etc
emails=$(grep -E -o '\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b' /etc/*)

# Creează fișierul emails.lst și adaugă adresele găsite, separate prin virgulă
echo "$emails" | tr '\n' ',' > emails.lst

# Afișează conținutul fișierului emails.lst pe ecran
cat emails.lst

echo "\nProcesul de extragere și afișare a adreselor de e-mail a fost finalizat."

