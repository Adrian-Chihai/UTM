# Crearea listei "my_list"
my_list <- list(
  list(5),
  list(c(160, 180, 175)),
  matrix(1:12, nrow = 4),
  data.frame(
    height = c(160, 180, 175),
    weight = c(52, 96, 60),
    age = c(18, 43, 29),
    c.eyes = c("green", "blue", "blue")
  )
)

# Afișarea listei
print(my_list)

# Dăm nume elementelor listei
names(my_list) <- c("element1", "element2", "element3", "element4")
print(my_list)

#extragerea celui de al doilea element
#in vector
element2_vector <- unlist(my_list[2])
print(element2_vector)
#in lista
element2_list <- my_list[[2]]
print(element2_list)


#extragerea elementului 1 si 3
element1_3 <- my_list[c("element1", "element3")]
print(element1_3)

# Accesăm al treilea element al celui de-al patrulea compartiment
element4_matrix <- my_list[["element4"]]
element <- element4_matrix[3, 2]
element4_matrix[3, 2] <- paste(element)

# Afișăm matricea actualizată
print(element4_matrix)
