#!/bin/bash

# Setarea modului initial
current_mode="add"
current_value=1

# Functie pentru adunare
function add() {
  current_value=$((current_value + $1))
}

# Functie pentru inmultire
function multiply() {
  current_value=$((current_value * $1))
}

# Functie pentru afisarea mesajului de final
function finish() {
  echo "Script finalized. Result: $current_value"
  exit 0
}

# Numele fișierului ascuns
hidden_file=".hidden_file"

# Verificare existență fișier
if [ ! -e "$hidden_file" ]; then
  echo "Fișierul ascuns $hidden_file nu există. Porniți Generatorul."
  exit 1
fi

# Procesarea fișierului ascuns
while read -r line; do
  case "$line" in
    "+")
      current_mode="add"
      ;;
    "*")
      current_mode="multiply"
      ;;
    [0-9]*)
      case "$current_mode" in
        "add")
          add "$line"
          ;;
        "multiply")
          multiply "$line"
          ;;
      esac
      ;;
    *)
      echo "Eroare: Date de intrare eronate."
      exit 1
      ;;
  esac
done < "$hidden_file"

echo "Rezultatul calculat este $current_value"

