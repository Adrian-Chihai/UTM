import pandas as pd

def get_data_for_week(target_date):
    # Citirea datelor din fișierul CSV
    df = pd.read_csv('rezultat_complet.csv', delimiter=',')

    # Convertiți coloana 'D' la tipul de date dată
    df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

    # Găsirea intervalului de date pentru săptămâna corespunzătoare
    start_of_week = target_date - pd.DateOffset(days=target_date.weekday())
    end_of_week = start_of_week + pd.DateOffset(days=6)

    # Filtrare pentru datele care corespund intervalului de săptămână
    result_data = df[(df['D'] >= start_of_week) & (df['D'] <= end_of_week)]

    # Verificați dacă există date pentru acea săptămână
    if not result_data.empty:
        return result_data.reset_index(drop=True)  # Resetarea indexului pentru a afișa toate coloanele
    else:
        return None

def display_data_for_weeks(start_date, end_date):
    # Convertiți datele de intrare la tipul datetime
    start_date = pd.to_datetime(start_date, format='%Y-%m-%d')
    end_date = pd.to_datetime(end_date, format='%Y-%m-%d')

    # Iterare prin fiecare săptămână între start_date și end_date
    current_date = start_date
    while current_date <= end_date:
        result_for_week = get_data_for_week(current_date)

        if result_for_week is not None:
            # Afișarea completă a DataFrame-ului pentru săptămâna respectivă
            with pd.option_context('display.max_columns', None, 'display.max_rows', None):
                print(f"Datele pentru săptămâna {current_date.strftime('%d.%m.%Y')} - {end_date.strftime('%d.%m.%Y')} sunt:")
                print(result_for_week)
        else:
            print(f"Nu există date pentru săptămâna {current_date.strftime('%d.%m.%Y')} - {end_date.strftime('%d.%m.%Y')}.")

        # Trecerea la următoarea săptămână
        current_date += pd.DateOffset(weeks=1)

# Exemplu de utilizare:
start_date = '2023-11-26'
end_date = '2023-12-30'
display_data_for_weeks(start_date, end_date)
