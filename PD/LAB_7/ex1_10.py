from decimal import Decimal, getcontext
from fractions import Fraction
from sympy import symbols, expand, sin, cos, simplify, limit, Not, Or, And, log, diff, Eq, solve, satisfiable, Function, dsolve, Derivative
from scipy.integrate import quad
import numpy as np

def ex1():
    #setam numaru de cifre
    getcontext().prec = 102
    #functia pentru calcularea sqrt(2)
    radacina_doua = Decimal(2).sqrt()
    print(radacina_doua)

def ex2():
    #declararea fractiilor
    frac1 = Fraction(1, 2)
    frac2 = Fraction(1, 3)
    suma_frac = frac1 + frac2
    print(suma_frac)
def ex3():
    #definirea variabilelor
    x, y = symbols('x y')

    #definirea expresiei
    expresie = (x + y) ** 6

    #rezultatul extins
    rez_extins = expand(expresie)
    print(rez_extins)

def ex4():
    #definim variabila x
    x = symbols('x')

    #expresia trigonometrică
    expresie_trigonometrica = sin(x) / cos(x)

    #simplificarea expresiei
    expresie_simplificata = simplify(expresie_trigonometrica)

    print(expresie_simplificata)

def ex5():
    #definim variabila x
    x = symbols('x')

    #expresia
    expresie = sin(x) / x

    rez = limit(expresie, x, 0)
    print(rez)

def ex6():
    x = symbols('x')
    expresie = log(x)
    derivata = diff(expresie, x)
    print(derivata)

def ex7():
    x, y = symbols('x y')
    ecuatie1 = Eq(2 * x + 3 * y, 5)
    ecuatie2 = Eq(4 * x - 3 * y, -4)
    sol = solve((ecuatie1, ecuatie2), (x, y))
    print(sol)
def ex8():
    x, y = symbols('x y')

    expresie = And(Or(x, Not(y)), Or(y, Not(x)))

    sol = satisfiable(expresie, all_models=True)

    for model in sol:
        print(f'x={model[x]}, y={model[y]} fac expresia adevărată.')

def ex9_Simplu():
    x = symbols('x')
    f = Function('f')
    ecuatie = x * Derivative(f(x), x) + f(x) - f(x) ** 2
    sol_simpla = dsolve(ecuatie)
    print(sol_simpla)
def ex9_Bernoulli():
    x = symbols('x')
    f = Function('f')
    ecuatie = x * Derivative(f(x), x) + f(x) - f(x) ** 2
    sol_bernoulli = dsolve(ecuatie, hint='Bernoulli')
    print(sol_bernoulli)

def ex10():
    def functie(x):
        return np.cos(2 * np.pi * x)

    res, eroare = quad(functie, 0,1)
    print(f"res = {res}")
    print(f"eroare = {eroare}")

functions_list = [ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9_Simplu, ex9_Bernoulli, ex10]

for index, func in enumerate(functions_list, start=1):
    if index == 10 or index == 11:
        ex_name = f"Ex nr = {index - 1}_bernoulli" if index == 10 else f"Ex nr = {index - 1}"
        print(ex_name)
    else:
        print(f"Ex nr = {index}")
    func()
    print("\n" + "-"*50 + "\n")
