print("Ex 3")
nr1 = 4
nr2 = 5

suma = lambda a, b: a + b
diferenta = lambda a, b: a - b

print("Sum -> ", suma(nr1, nr2))
print("Dif -> ", diferenta(nr1, nr2))

resSuma = list(map(suma, [2, 2], [nr1, nr2]))
resDiferenta = list(map(diferenta, [0, 0], [nr1, nr2]))

print(f"Suma ->  {resSuma}")
print(f"Diferenta -> {resDiferenta}")
