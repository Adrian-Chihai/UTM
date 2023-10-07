#ex3
#cream un vector cu datele de la 1 pana la 10
vector=1:10
print(vector)
#cream o matrice pe care o impartim in 2 coloane 
#datele le luam din cadrul vectorului
matrice1=matrix(vector, ncol=2)
print(matrice1)
#cream o matrice pe care o impartim in 2 randuri
#datele introduse sunt de la 1 pana la 10 
matrice2=matrix(1:10,nrow=2,byrow=T)
print(matrice2)
#cream o matrice in care pe fiecare rand introducem datele de la 1 pana la 4
m=matrix(1:4,nrow=4,ncol=4)
print(m)

#ex4
print("Matrice1 nr randuri -> ")
print(nrow(matrice1))
#dimensiunile matrice1
dim(matrice1)
#nr de coloane al matricei1
ncol(matrice1)
#nr de randuri al matricei1
nrow(matrice1)
#imultim matrice1 cu matrice2 cu ajutorul %%
resultat= matrice1 %*% matrice2
print(resultat)

#printam transpusa unei matrici
print(t(matrice1))

#printam diagonala matricei resultat 
#creata in urma inmultirii celor 2
print(diag(resultat))

#printam o diagonala intr-o matrice cu 0
print(diag(c(3,2,4)))

vector1 <- c(8,4,5)
vector2 <- c(3,4,2)
res = rbind(vector1, vector2)
print(res)
vector3 <- c(9,3)
print(cbind(res, vector3))

print(eigen(m))

#ex5
#printeaza primul rand al matricii resultat
print(resultat[1,])
#printeaza matricea resultat in care randdul 2 e copiat in 1
print(resultat[, c(2,2,1)])
#elimina primul rand din matricea resultat
print(resultat[-1,])
#printeaza elementele mai mari ca 51
print(resultat[resultat>51])
#printeaza o matrice creata din vector
print(matrix(vector,nrow=2))
#printeaza o matrice creata din vector conform paramaterului byRow care e true
print(matrix(vector,nrow=2, byrow=T))

#ex6
#Crearea matricei Y
Y <- matrix(c(1, 2, 3, 5, 10, 12, 13, 22, 5, 9, 8, 34, 7, 1, 4, 3), nrow = 4, byrow = TRUE)
print(Y)
#Accesarea elementului din al treilea rând și a doua coloană
element <- Y[3, 2]
print(element)
#Accesarea întregului al doilea rând
rand_doi <- Y[2, ]
print(rand_doi)
#Accesarea întregii a patra coloane
coloana_patru <- Y[, 4]
print(coloana_patru)
#Eliminarea primului rand si celei de a doua coloana
matrice_noua <- Y[-1, -2]
print(matrice_noua)
#Exportarea matricea Y într-un fișier .txt folosind următorul cod
write.table(Y, "D:/UNIVER/ANUL_3/PD/LAB_1/PD_LAB_1/data.txt", sep = "\t", row.names = FALSE, col.names = FALSE)




