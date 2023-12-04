import pandas as pd

def convert_date(date_str):
    return pd.to_datetime(date_str, format='%d.%m.%Y')

def convert_to_numeric(value):
    try:
        return float(value)
    except ValueError:
        return None

df = pd.read_csv('rezultat_complet.csv', parse_dates=['D'], date_parser=convert_date)
df_numeric = df.applymap(convert_to_numeric)

min_pe_zi = df_numeric.min(axis=1)
max_pe_zi = df_numeric.max(axis=1)

min_total = df_numeric.min().min()
max_total = df_numeric.max().max()

print("Minim pe zi:")
print(min_pe_zi)
print("\nMaxim pe zi:")
print(max_pe_zi)

print("\nMinim total:")
print(min_total)
print("\nMaxim total:")
print(max_total)
