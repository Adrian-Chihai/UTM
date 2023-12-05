import pandas as pd
import os

df = pd.read_csv('rezultat_complet.csv', delimiter=',')

df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

df.set_index('D', inplace=True)

date_ranges = pd.date_range(start=df.index.min(), end=df.index.max(), freq='7D')

output_directory = 'weeks'

if not os.path.exists(output_directory):
    os.makedirs(output_directory)

for i in range(len(date_ranges) - 1):
    start_date = date_ranges[i]
    end_date = date_ranges[i + 1]

    week_data = df[(df.index >= start_date) & (df.index < end_date)]

    if not week_data.empty:
        file_path = os.path.join(output_directory, f"{start_date.strftime('%Y%m%d')}_{end_date.strftime('%Y%m%d')}.csv")
        week_data.to_csv(file_path, sep='\t')

print("Fișierele au fost create cu succes în directorul 'weeks'.")
