#!/bin/bash

# Verifică dacă există directorul trash în directorul home
trash_dir="$HOME/trash"
if [ ! -d "$trash_dir" ]; then
    mkdir "$trash_dir"
fi

# Numele fișierului transmis ca parametru
file_name="$1"

# Verifică dacă parametrul a fost furnizat
if [ -z "$file_name" ]; then
    echo "Eroare: Numele fișierului trebuie specificat."
    exit 1
fi

# Verifică dacă fișierul există în directorul curent
if [ ! -e "$file_name" ]; then
    touch "$file_name"
fi

# Creează un fișier cu numele parametrului în directorul curen

# Creează un hard link cu un nume unic în directorul trash
link_name="$trash_dir/$(date +%s)"
ln "$file_name" "$link_name"

# Șterge fișierul din directorul curent
rm "$file_name"

# Adaugă o linie în trash.log
echo "$(realpath "$file_name") $link_name" >> "$trash_dir/trash.log"

echo "Fișierul '$file_name' a fost mutat în directorul trash."

