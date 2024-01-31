#!/bin/bash

# Navighează către directorul /bin
cd /bin

# Creează un array pentru a stoca interpretorii și numărul de utilizări
declare -A interpreters

# Caută toate fișierele ce încep cu "#!/bin/" și actualizează array-ul
for file in *; do
    if [ -f "$file" ] && head -n 1 "$file" | grep -q '^#!/bin/'; then
        interpreter=$(head -n 1 "$file" | cut -d' ' -f1)
        ((interpreters["$interpreter"]++))
    fi
done

# Afișează interpretorii și numărul de utilizări
for interpreter in "${!interpreters[@]}"; do
    echo "$interpreter: ${interpreters[$interpreter]} utilizări"
done

