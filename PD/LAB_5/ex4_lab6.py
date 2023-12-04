import pandas as pd

def get_data_for_date(target_date):
    # Citirea datelor din fișierul CSV
    df = pd.read_csv('rezultat_complet.csv', delimiter=',')

    # Convertiți coloana 'D' la tipul de date dată
    df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

    # Filtrare pentru datele care corespund datei target
    result_data = df[df['D'] == target_date]

    # Verificați dacă există date pentru această dată
    if not result_data.empty:
        return result_data.reset_index(drop=True)  # Resetarea indexului pentru a afișa toate coloanele
    else:
        return None

# Exemplu de utilizare:
target_date = pd.to_datetime('2023-12-10', format='%Y-%m-%d')
result_for_date = get_data_for_date(target_date)

if result_for_date is not None:
    with pd.option_context('display.max_columns', None, 'display.max_rows', None):
        print(f"Datele pentru data {target_date.strftime('%d.%m.%Y')} sunt:")
        print(result_for_date)
else:
    print(f"Nu există date pentru data {target_date.strftime('%d.%m.%Y')}.")
