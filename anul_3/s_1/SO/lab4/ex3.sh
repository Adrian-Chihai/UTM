#/bin/bash

# Filtrăm mesajele informaționale și le adăugăm la full.log
journalctl -u gdm | grep -E '\<INFO\>' | sed 's/\(II\)/Information:/g' > full.log

# Adăugăm mesajele de avertizare la full.log
journalctl -u gdm | grep -E '\<WARNING\>' | sed 's/\(WW\)/Warning:/g' >> full.log

# Afișăm conținutul fișierului full.log
cat full.log
