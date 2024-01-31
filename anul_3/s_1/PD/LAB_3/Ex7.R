# Valorile x între -6 și 6
x <- seq(-6, 6, by = 0.01)

# Calculam densitatea normală pentru fiecare valoare x
densitate_normala <- dnorm(x)

# Desenam graficul distribuției normale
plot(x, densitate_normala, type = "l", lwd = 2, col = "blue",
     xlab = "Valoarea x", ylab = "Densitatea", main = "Distribuție normală între -6 și 6")

abline(v = 0, col = "red", lty = 2)

# Adăugam o legendă
legend("topright", legend = "Distribuție normală între -6 și 6", col = "blue", lwd = 2)

