import pandas as pd

def get_data_for_date(target_date):
    df = pd.read_csv('rezultat_complet.csv', delimiter=',')

    df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

    result_data = df[df['D'] == target_date]

    if not result_data.empty:
        return result_data.reset_index(drop=True)
    else:
        return None

target_date = pd.to_datetime('2023-12-10', format='%Y-%m-%d')
result_for_date = get_data_for_date(target_date)

if result_for_date is not None:
    with pd.option_context('display.max_columns', None, 'display.max_rows', None):
        print(f"Datele pentru data {target_date.strftime('%d.%m.%Y')} sunt:")
        print(result_for_date)
else:
    print(f"Nu există date pentru data {target_date.strftime('%d.%m.%Y')}.")
