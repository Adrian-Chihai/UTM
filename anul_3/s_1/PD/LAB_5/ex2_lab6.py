import pandas as pd

df = pd.read_csv('rezultat_complet.csv', delimiter=',')

df['D'] = pd.to_datetime(df['D'], format='%d.%m.%Y')

df.set_index('D', inplace=True)

daily_min = df.min(axis=1)
daily_max = df.max(axis=1)

daily_results = pd.DataFrame({'Min': daily_min, 'Max': daily_max})

overall_min = df.min().min()
overall_max = df.max().max()

overall_avg_min_max = df[['H Temp_bardar', 'L Temp_bardar', 'H Temp_amsterdam', 'L Temp_amsterdam', 'H Temp_chisinau', 'L Temp_chisinau']].agg(['min', 'max']).mean()

print("Valoarea minimă și maximă pentru fiecare zi:")
print(daily_results)

print("\nValoarea minimă și maximă pentru toate datele:")
print(f"Minim global: {overall_min}")
print(f"Maxim global: {overall_max}")

print("\nMedia pentru valoarea minimă și maximă a tuturor datelor:")
print(overall_avg_min_max)
