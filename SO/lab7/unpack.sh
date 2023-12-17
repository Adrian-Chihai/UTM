#!/bin/bash

# Directorul de restore
restore_dir="$HOME/restore"

# Găsim directorul de backup cu cea mai recentă dată în nume
latest_backup=$(find "$HOME" -maxdepth 1 -type d -name "Backup-*" | sort -r | head -n 1)

if [ -z "$latest_backup" ]; then
  echo "Nu există niciun director de backup."
  exit 1
fi

# Copiem fișierele din directorul de backup în directorul de restore
cp -r "$latest_backup"/* "$restore_dir"

# Eliminăm versiunile anterioare
find "$restore_dir" -type f -name "*.*" -delete

echo "Restore complet în $restore_dir."

