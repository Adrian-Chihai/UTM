#!/bin/bash

# Data pentru directorul de backup
backup_date=$(date +"%Y%m%d")

# Directorul sursă
source_dir="$HOME/source"

# Directorul de backup
backup_dir="$HOME/Backup-$backup_date"

# Fișierul raport de backup
backup_report="$HOME/backup-report"

# Verificăm dacă există deja un director de backup creat în ultimele 7 zile
latest_backup=$(find "$HOME" -maxdepth 1 -type d -name "Backup-*" -ctime -7 -print -quit)

if [ -z "$latest_backup" ]; then
  # Dacă nu există, creăm un nou director de backup
  mkdir "$backup_dir"

  # Copiem fișierele din directorul sursă în noul director de backup
  cp -r "$source_dir"/* "$backup_dir"

  # Scriem informații în fișierul de raport
  echo "Backup directory $backup_dir created on $backup_date" >> "$backup_report"
  echo "Files copied:" >> "$backup_report"
  ls -l "$backup_dir" >> "$backup_report"
else
  # Dacă există deja un director de backup, copiem fișierele din sursă
  # cu gestionarea versiunilor și actualizăm raportul de backup
  for file in "$source_dir"/*; do
    filename=$(basename "$file")
    destination="$backup_dir/$filename"
    
    if [ -e "$destination" ]; then
      # Dacă fișierul există deja în directorul de backup, verificăm dimensiunile
      size_source=$(stat -c %s "$file")
      size_destination=$(stat -c %s "$destination")

      if [ "$size_source" -ne "$size_destination" ]; then
        # Dacă dimensiunile sunt diferite, creăm o nouă versiune
        mv "$destination" "$destination.$backup_date"
        cp "$file" "$destination"
        echo "File $filename updated in $backup_dir on $backup_date" >> "$backup_report"
      fi
    else
      # Dacă fișierul nu există în directorul de backup, îl copiem
      cp "$file" "$destination"
      echo "File $filename copied to $backup_dir on $backup_date" >> "$backup_report"
    fi
  done
fi

