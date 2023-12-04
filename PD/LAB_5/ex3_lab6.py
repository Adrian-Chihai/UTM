import pandas as pd
import os

# Citirea datelor din fișierul CSV
df = pd.read_csv('rezultat_complet.csv', delimiter=',')

# Convertiți coloana 'D' la tipul de date dată
df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

# Setați coloana 'D' ca index pentru grupare
df.set_index('D', inplace=True)

# Obțineți un interval de date pentru fiecare săptămână
date_ranges = pd.date_range(start=df.index.min(), end=df.index.max(), freq='7D')

# Directorul în care vor fi salvate fișierele
output_directory = 'weeks'

# Verificați dacă directorul există, altfel creați-l
if not os.path.exists(output_directory):
    os.makedirs(output_directory)

# Iterați prin intervalele de date și creați fișiere separate
for i in range(len(date_ranges) - 1):
    start_date = date_ranges[i]
    end_date = date_ranges[i + 1]

    # Selecționați rândurile din DataFrame pentru intervalul curent
    week_data = df[(df.index >= start_date) & (df.index < end_date)]

    # Verificați dacă există date în interval înainte de a crea fișierul
    if not week_data.empty:
        # Construiți calea și numele fișierului
        file_path = os.path.join(output_directory, f"{start_date.strftime('%Y%m%d')}_{end_date.strftime('%Y%m%d')}.csv")

        # Salvarea datelor într-un fișier CSV
        week_data.to_csv(file_path, sep='\t')

# Afișarea unui mesaj de finalizare
print("Fișierele au fost create cu succes în directorul 'weeks'.")
