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

print("Matrice1 nr randuri -> ", nrow(matrice1))
#dimensiunile matrice1
dim(matrice1)
#nr de coloane al matricei1
ncol(matrice1)
#nr de randuri al matricei1
nrow(matrice1)
#imultim matrice1 cu matrice2 cu ajutorul %%
resultat= matrice1 %*% matrice2
print(resultat)

#ex4