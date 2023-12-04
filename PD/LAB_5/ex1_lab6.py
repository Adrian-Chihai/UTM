import pandas as pd

import pandas as pd

def concat_all():
    # Încarcă datele din cele trei fișiere CSV
    bardar = pd.read_csv('bardar_weather.csv')
    amsterdam = pd.read_csv('amsterdam_weather.csv')
    chisinau = pd.read_csv('chisinau_weather.csv')

    # Elimină duplicatelor bazate pe coloana 'D' și 'Oras'
    bardar.drop_duplicates(subset=['D'], inplace=True)
    amsterdam.drop_duplicates(subset=['D'], inplace=True)
    chisinau.drop_duplicates(subset=['D'], inplace=True)

    # Asigură-te că DataFrames au aceleași coloane și același index
    bardar.set_index('D', inplace=True)
    amsterdam.set_index('D', inplace=True)
    chisinau.set_index('D', inplace=True)

    # Converteste valorile la tip numeric
    bardar = bardar.apply(pd.to_numeric, errors='coerce')
    amsterdam = amsterdam.apply(pd.to_numeric, errors='coerce')
    chisinau = chisinau.apply(pd.to_numeric, errors='coerce')

    # Realizează îmbinarea utilizând coloanele 'D' și 'Oras' pentru a evita multiplicarea datelor
    df_regrupat = pd.merge(bardar, amsterdam, left_index=True, right_index=True, suffixes=('_bardar', '_amsterdam'))
    df_regrupat = pd.merge(df_regrupat, chisinau.add_suffix('_chisinau'), left_index=True, right_index=True)

    # Resetarea indexului pentru a reveni la forma originală
    df_regrupat.reset_index(inplace=True)

    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)

    # Salvează DataFrame-ul într-un fișier CSV
    df_regrupat.to_csv('rezultat_complet.csv', index=False)

    return df_regrupat

# Restul codului rămâne neschimbat

def search_dates(df_regrupat):
    min_pe_zi = df_regrupat.min(axis=1)
    max_pe_zi = df_regrupat.max(axis=1)
    media_pe_zi = df_regrupat.mean(axis=1)

    # Calculul pentru toate datele stocate
    min_total = df_regrupat.min().min()
    max_total = df_regrupat.max().max()
    media_total = df_regrupat.mean().mean()

    # Afisează rezultatele
    print("Minim pe zi:")
    print(min_pe_zi)
    print("\nMaxim pe zi:")
    print(max_pe_zi)
    print("\nMedie pe zi:")
    print(media_pe_zi)

    print("\nMinim total:")
    print(min_total)
    print("\nMaxim total:")
    print(max_total)
    print("\nMedie totală:")
    print(media_total)


df = concat_all()
search_dates(df)
