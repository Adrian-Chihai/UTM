#!/bin/bash

# Extrage cuvintele cu cel puțin 3 caractere din manualul bash
words=$(man bash | grep -o '\b\w\{3,\}\b' | tr '[:upper:]' '[:lower:]')

# Numara aparitiile fiecarui cuvant si le sorteaza
word_counts=$(echo "$words" | sort | uniq -c | sort -rn)

# Afișează primele 5 rezultate
echo "$word_counts" | head -n 5

