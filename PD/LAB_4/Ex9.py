print("Ex9")

def printSumNr(limit):
    if(limit == 1):
        return 1
    return limit + printSumNr(limit - 1)

limit = 10
print(f"Suma primelor {limit} numere este {printSumNr(limit)}")