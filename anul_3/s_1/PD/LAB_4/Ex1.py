print("EX: 1")
mathNr = {
    "pi": 3.1415926,
    "e": 2.71828,
    "phi": 1.61803
}

print(mathNr.keys())

tuples = [(k, v) for k, v in mathNr.items()]
print(tuples)