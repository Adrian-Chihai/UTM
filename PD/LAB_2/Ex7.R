#setul de date "iris"
data(iris)

#vizualizarea primelor 7 linii din setul de date
head(iris, 7)

#crearea unui subset care contine versicolor si species
new.iris <- subset(iris, Species == "versicolor")
print(new.iris)

#sortarea in descrestere in functie cu variabila Sepal.Length
new.iris <- new.iris[order(-new.iris$Sepal.Length), ]
print(new.iris)