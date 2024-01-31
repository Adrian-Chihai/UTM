library(datasets)

# Încărcați setul de date "Orange"
data(Orange)

# Desenați graficul de dispersie
plot(Orange$age, Orange$circumference, pch = 6, col = "green", xlab = "", ylab = "")