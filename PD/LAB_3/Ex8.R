library(ggplot2)

# Definim un set de valori pentru x în intervalul [-2, 2]
x <- seq(-2, 2, by = 0.01)

# Calculam valorile corespunzătoare pentru funcția f(x)
y <- x^5 + x^3 - 3 * x

# Cream un cadru de date cu valorile x și y
data <- data.frame(x = x, y = y)

# Cream un grafic folosind ggplot2
grafic <- ggplot(data, aes(x, y)) +
  geom_line(color = "black", size = 2) +
  labs(title = "Graficul functiei f(x) = x^5 + x^3 - 3x",
       x = "x",
       y = "f(x)")

# Afișam graficul
print(grafic)
