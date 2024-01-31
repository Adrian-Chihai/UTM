import requests
from bs4 import BeautifulSoup
import csv
#modifica temperatura daca e -- sa returnezi o valoare medie
def fahrenheit_to_celsius(fahrenheit):
    if '--' in fahrenheit:
        return 0
    if '°' in fahrenheit:
        fahrenheit = fahrenheit.replace('°', '')
    return round((float(fahrenheit) - 32) * 5 / 9)


def get_weather(url, csv_name):
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.text, 'html.parser')

        buttons = soup.find_all('button', class_='Button--default--2gfm1')

        with open(f'{csv_name}.csv', 'w', newline='') as csvfile:
            fieldnames = ['D', 'H Temp', 'L Temp']
            csv_writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

            csv_writer.writeheader()

            for button in buttons:
                date_element = button.find('span', class_='CalendarDateCell--date--JO3Db')
                if date_element is None:
                    continue

                date = date_element.text.strip()

                high_temp_element = button.find('div', class_='CalendarDateCell--tempHigh--3k9Yr')
                low_temp_element = button.find('div', class_='CalendarDateCell--tempLow--2WL7c')

                if high_temp_element is None or low_temp_element is None:
                    continue

                high_temp_str = high_temp_element.text.strip()
                low_temp_str = low_temp_element.text.strip()

                high_temp_c = fahrenheit_to_celsius(high_temp_str)
                low_temp_c = fahrenheit_to_celsius(low_temp_str)

                csv_writer.writerow(
                    {'D': date, 'H Temp': high_temp_c, 'L Temp': low_temp_c})

                print(f"Date: {date}")
                print(f"High Temperature: {high_temp_c}°C")
                print(f"Low Temperature: {low_temp_c}°C")
                print("\n")

        print("Weather data saved to weather_data.csv")

    else:
        print(f"Failed to retrieve the page. Status code: {response.status_code}")


url_bardar = "https://weather.com/weather/monthly/l/c0150b8b0b198f915a47db72ba5d723deab5dfeedec7f099aed7f602e2f8d06d"
get_weather(url_bardar, 'bardar_weather')

url_chisinau = "https://weather.com/weather/monthly/l/c0150b8b0b198f915a47db72ba5d723deab5dfeedec7f099aed7f602e2f8d06d"
get_weather(url_chisinau, 'chisinau_weather')

url_amsterdam = "https://weather.com/weather/monthly/l/968d2f1a5509a2f71fca25929b7d83139ac5134f61611a9c6637c90354cd6da8"
get_weather(url_amsterdam, 'amsterdam_weather')
