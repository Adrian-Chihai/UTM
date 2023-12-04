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
    print(df_regrupat)

    # Salvează DataFrame-ul într-un fișier CSV
    df_regrupat.to_csv('rezultat_complet.csv', index=False)

concat_all()
