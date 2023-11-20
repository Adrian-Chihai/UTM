from bs4 import BeautifulSoup
import requests
from urllib.parse import urljoin

def wiki_title(url):
    res = requests.get(url)
    soup = BeautifulSoup(res.text, "html.parser")

    title = soup.find('title').text
    print(f"Title:\n{title}\n")

def wiki_sections(url):
    res = requests.get(url)
    soup = BeautifulSoup(res.text, 'html.parser')
    sections = soup.find_all(['h1', 'h2', 'h3', 'h4', 'h5', 'h6'])
    print(f"Sections")
    for section in sections:
        print(f"{section.text.strip()}")

def wiki_photo(url):
    res = requests.get(url)
    soup = BeautifulSoup(res.text, 'html.parser')
    figure_element = soup.find('figure', class_='mw-default-size')
    image_element = figure_element.find('img')
    image_url = image_element['src']
    absolute_image_url = urljoin(url, image_url)
    caption = figure_element.find('figcaption').text
    print(f"Image URL: {absolute_image_url}")
    print(f"Caption: {caption}")
    image_response = requests.get(absolute_image_url)
    with open("downloaded_image.png", "wb") as f:
        f.write(image_response.content)

    print("Image downloaded successfully.")


url = "https://en.wikipedia.org/wiki/Steam_(service)"
wiki_title(url)
wiki_sections(url)
wiki_photo(url)


