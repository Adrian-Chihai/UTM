import pandas as pd

def concatenate_all():
    bardar = pd.read_csv('bardar_weather.csv')
    amsterdam = pd.read_csv('amsterdam_weather.csv')
    chisinau = pd.read_csv('chisinau_weather.csv')

    bardar.drop_duplicates(subset=['D'], inplace=True)
    amsterdam.drop_duplicates(subset=['D'], inplace=True)
    chisinau.drop_duplicates(subset=['D'], inplace=True)

    bardar.set_index('D', inplace=True)
    amsterdam.set_index('D', inplace=True)
    chisinau.set_index('D', inplace=True)

    bardar = bardar.apply(pd.to_numeric, errors='coerce')
    amsterdam = amsterdam.apply(pd.to_numeric, errors='coerce')
    chisinau = chisinau.apply(pd.to_numeric, errors='coerce')

    df_regrupat = pd.merge(bardar, amsterdam, left_index=True, right_index=True, suffixes=('_bardar', '_amsterdam'))
    df_regrupat = pd.merge(df_regrupat, chisinau.add_suffix('_chisinau'), left_index=True, right_index=True)

    df_regrupat.reset_index(inplace=True)

    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)
    print(df_regrupat)

    df_regrupat.to_csv('rezultat_complet.csv', index=False)

concatenate_all()
