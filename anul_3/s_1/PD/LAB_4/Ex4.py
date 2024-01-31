from random import randint
print("Ex 4")
arr = [randint(1, 30) for _ in range(25)]
print(sorted(arr))
distinctElem = list(filter(lambda e: arr.count(e) == 1, arr))
print(sorted(distinctElem))