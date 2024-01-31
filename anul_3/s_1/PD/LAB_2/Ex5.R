#array (8 2 6) se repeta de 3 ori
k <- rep(c(8, 2, 6), times = 3)
print(k)
#din array (4 9 2) se repeta 4 de 7 ori, 9 de 5 ori si 2 de 3 ori  
w <- rep(c(4, 9, 2), times = c(7, 5, 3))
print(w)
#din array (4 9 2) se repeta 4 de 7 ori, 9 de 5 ori si 2 de 3 ori  
w <- c(rep(4, 7), rep(9, 5), rep(2, 3))
print(w)
