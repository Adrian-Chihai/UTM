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

            book_items = soup.find_all('article', class_='product_pod')

            for book_item in book_items:
                title_element = book_item.find('h3')
                if title_element:
                    title = title_element.a['title']

                    rating_element = book_item.find('p', class_='star-rating Two')

                    if rating_element:
                        books.append(title)

            page_nr += 1
        else:
            print(f"Can't go to page {page_nr}\n")
            break;

    return books


url = "http://books.toscrape.com"
books = find_rating(url)

print(f"{len(books)} have been founded")
for book in books:
    print(book)
