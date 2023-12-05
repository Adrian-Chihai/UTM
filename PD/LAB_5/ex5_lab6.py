import pandas as pd

def display_data_for_weeks(target_date, df):
    if 'D' not in df.columns or not pd.api.types.is_datetime64_any_dtype(df['D']):
        print("Coloana 'D' nu există sau nu este convertită corect la tipul de dată datetime.")
        return

    start_of_week = target_date - pd.DateOffset(days=target_date.weekday())
    end_of_week = start_of_week + pd.DateOffset(days=6)

    result_data = df[(df['D'] >= start_of_week) & (df['D'] <= end_of_week)]

    if not result_data.empty:
        with pd.option_context('display.max_columns', None, 'display.max_rows', None):
            print(f"Datele pentru săptămâna {start_of_week.strftime('%U-%Y')} sunt:")
            print(result_data)
    else:
        print(f"Nu există date pentru săptămâna {start_of_week.strftime('%U-%Y')}.")

df = pd.read_csv('rezultat_complet.csv', delimiter=',')
df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

target_date = pd.to_datetime('4.12.2023', format='%d.%m.%Y')
display_data_for_weeks(target_date, df)
