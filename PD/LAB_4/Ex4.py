#Ex 4
from random import randint

arr = [randint(1, 30) for _ in range(50)]
print(sorted(arr))
distinctElem = list(filter(lambda e: arr.count(e) == 1, arr))
print(sorted(distinctElem))