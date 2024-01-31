from functools import reduce
print("Ex 5")
def wordChar(word, charachter):
    return reduce(lambda app, c: app + 1 if c == charachter else app, word, 0)

print("Nr de aparitii al characterului 'a' in cuvantul 'aparitiile lui a' este de -> ",
      wordChar("aparitiile lui a", "a"))