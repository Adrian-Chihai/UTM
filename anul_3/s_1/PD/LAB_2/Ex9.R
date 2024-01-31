# Crearea cadru de date "person"
person <- data.frame(
  height = c(160, 180, 175),
  weight = c(52, 96, 60),
  age = c(18, 43, 29),
  c.eyes = c("green", "blue", "blue")
)
print(person)

# 1) Schimbarea numelui coloanei 3 cu "new.age"
names(person)[3] <- "new.age"
print(person)

# 2) Schimbarea numelui liniei 2 cu "Mary"
rownames(person)[2] <- "Mary"
print(person)

# 3) Ștergerea numelor rândurilor
rownames(person) <- NULL
print(person)

# 4) Schimbarea tuturor numelor coloanelor cu a, b, c, d
names(person) <- c("a", "b", "c", "d")
print(person)

# 5) Extrageți elementul rândului 1 și al coloanei 3
element_1_3 <- person[1, 3]
print(element_1_3)

# 6) Extrageți variabila 2 (rezultat în data.frame, rezultat în vector)
variable_2_df <- person[, 2]
print(variable_2_df)
variable_2_vector <- person[, 2]
print(variable_2_vector)

# 7) Extrageți elementele 1 și 3 ale variabilei 3
elements_1_and_3 <- person[3, c(1, 3)]
print(elements_1_and_3)

# 8) Extrageți valorile mai mari de 160 și mai mici de 180 ale variabilei "height"
height_values <- person$a[person$a > 160 & person$a < 180]
print(height_values)

# 9) Extrageți valorile greutății persoanelor ale căror valori de înălțime sunt mai mari de 170
weight_values <- person$b[person$a > 170]
print(weight_values)

# 10) Extrageți toate persoanele care au o greutate mai mare de 52 kg
persons_with_weight_above_52 <- person[person$b > 52, ]
print(persons_with_weight_above_52)

# 11) Schimbarea înălțimii primelor 2 persoane la 190 și 158 și adăugarea unui comentariu la fiecare rând
person$a[1:2] <- c(190, 158)

print(person)
