#!/bin/bash

# Verifică dacă există directorul trash în directorul home
trash_dir="$HOME/trash"
if [ ! -d "$trash_dir" ]; then
    echo "Eroare: Directorul trash nu există."
    exit 1
fi

# Numele fișierului transmis ca parametru
file_name="$1"

# Verifică dacă parametrul a fost furnizat
if [ -z "$file_name" ]; then
    echo "Eroare: Numele fișierului trebuie specificat."
    exit 1
fi

# Verifică dacă fișierul trash.log există
log_file="$trash_dir/trash.log"
if [ ! -f "$log_file" ]; then
    echo "Eroare: Fișierul trash.log nu există."
    exit 1
fi

# Găsește înregistrările din trash.log cu numele specificat
entries=$(grep "$file_name" "$log_file")

# Verifică dacă există înregistrări
if [ -z "$entries" ]; then
    echo "Nu s-au găsit fișiere în trash.log cu numele '$file_name'."
    exit 0
fi

# Afiseaza toate numele găsite și cere confirmare pentru restaurarea tuturor
echo "Fișiere găsite în trash.log cu numele '$file_name':"
echo "$entries"

read -p "Doriți să restaurați toate fișierele? [y/N]: " response
case $response in
    [yY])
        # Restaurează toate fișierele găsite
        echo "$entries" | while read -r line; do
            source_file=$(echo "$line" | awk '{print $1}')
            trash_file=$(echo "$line" | awk '{print $2}')
            
            # Verifică dacă directorul există înainte de a crea link-ul
            restore_dir=$(dirname "$source_file")
            if [ ! -d "$restore_dir" ]; then
                echo "Directorul '$restore_dir' nu există. Se va restaura în directorul home/user."
                restore_dir="$HOME"
            fi

            ln "$trash_file" "$source_file" && rm "$trash_file"
            echo "Fișierul '$source_file' a fost restaurat cu succes în '$restore_dir'."
        done
        ;;
    *)
        echo "Nu s-au efectuat restaurări."
        ;;
esac

