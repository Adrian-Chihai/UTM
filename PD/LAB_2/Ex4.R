summary(data("Orange"))
last2 <- Orange[ , c("circumference", "age")]
summary(last2)
#Media pentru ultimle 2 variabile
mediaC <- mean(Orange$circumference)
print(media)
mediaA <- mean(Orange$age)
print(mediaA)

#Abaterea ultimelor 2 variabile
abatereaC <- sd(Orange$circumference)
print(abatereaC)
abatereaA <- sd(Orange$age)
print(abatereaA)

#Minimul si Maxim
maxC <- max(Orange$circumference)
print(maxC)
maxA <- max(Orange$age)
print(maxA)

minC <- min(Orange$circumference)
print(minC)
minA <- min(Orange$age)
print(minA)

#quartilele
quartilesC <- quantile(Orange$circumference, probs = c(0.25, 0.5, 0.75))
print(quartilesC)
quartilesA <- quantile(Orange$age, probs = c(0.25, 0.5, 0.75))
print(quartilesA)

#deciile
decileC <- apply(last2, 2, function(x) quantile(x, probs = seq(0, 1, by = 0.1)))
print(decileC)
