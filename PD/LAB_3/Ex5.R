library(ggplot2)

# Cream un set de date care conține valorile pentru x și valorile pentru funcția sinus
x <- seq(0.5, 3 * pi, length.out = 100)  # Generăm 100 de puncte în intervalul [0.5, 3π]
y <- sin(x)

# Creăm un obiect ggplot folosind datele noastre
grafic <- ggplot(data = data.frame(x = x, y = y), aes(x, y))

# Adăugăm o curbă pentru funcția sinus cu culoare roșie și grosimea liniei 15
grafic <- grafic + geom_line(color = "red", size = 1.5)

# Adăugăm titlul graficului
grafic <- grafic + labs(title = "Graficul funcției sinus")

# Afișăm graficul
print(grafic)
