print("Ex 7")


funList = [
    lambda c: c[1] if len(c) > 1 else None,
    lambda words: [word for word in words if word.isupper()],
    lambda s, c: s.find(c) if isinstance(s, str) else None
]

wordIndex = "Laborator"
findUppercase = ["HELlo", "ASFss", "UpPer", "ALONE"]
findC = "b"

for fun in funList:
    if funList.index(fun) == 0:
        print(fun(wordIndex))
    elif funList.index(fun) == 1:
        print(fun(findUppercase))
    else:
        print(fun(wordIndex, findC))