import requests
from bs4 import BeautifulSoup


def find_rating(default_url):
    books = []
    page_nr = 1

    while True:
        #structura pentru parcurgerea fiecarei pagini
        url = f"{default_url}/catalogue/page-{page_nr}.html"
        res = requests.get(url)

        if res.status_code == 200:
            soup = BeautifulSoup(res.text, 'html.parser')
            book_titles = soup.find_all('h3')
            stars = soup.find_all('p', class_='star-rating')
            for title, rating in zip(book_titles, stars):
                if 'Two' in rating['class']:
                    books.append(title.a['title'])

            change_page = soup.find('li', class_='next')
            if not change_page:
                break
            page_nr += 1
        else:
            print(f"Can't go to page {page_nr}")

    return books


url = "http://books.toscrape.com"
books = find_rating(url)

print(f"{len(books)} have been founded")
for book in books:
    print(book)
