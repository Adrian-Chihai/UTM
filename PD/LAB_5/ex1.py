import os

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
    print(f"Sections:")
    for section in sections:
        print(f"{section.text}")

def wiki_photo(url):
    res = requests.get(url)
    soup = BeautifulSoup(res.text, 'html.parser')
    images = soup.find_all('img')
    save_dir = 'img'

    counter = 0;
    print("\nImages: ")
    for image in images:
        name = f"image_{counter} : "
        link = image['src']
        absolute_url = urljoin(url, link)
        print(f"{name} : {absolute_url}")
        file_name = f'image_{counter}.jpg'
        file_path = os.path.join(save_dir, file_name)
        with open(file_path, 'wb') as f:
            im = requests.get(absolute_url)
            f.write(im.content)
        counter += 1

url = "https://en.wikipedia.org/wiki/Minecraft"
wiki_title(url)
wiki_sections(url)
wiki_photo(url)


