#printeaza elementele incepand de la 2 pana la 4 urmate de cele de la 9 pana la 13
x<-c(2:4,9:13)
print(x)
#printteaza caracterele din ""
y<-c("b", "c", "E")
print(y)
#printeaza elementul cu nr 5 din tabelul x
print(x[5])
#printeaza elementele de la indexul 2 pana la 3 din y
print(y[2:3])
#inlocuieste cele 3 elemente din variabila y conform indexului selectat
print(y[c(2,2,3)])
#printeaza elementul cu nr 5 din tabelul x
print(x[50])
#elimina elementul de la indexul indicat 
print(x[-5])
#printeaza indexul 4 din tabelul x
print(x[3])
#printeaza tabelul modificand indexul 1,2 si 3 conform indexului indicat dupa care printeaza toat elementele pana la pozitia 7
print(x[c(2,2,5 :7)])
#printeaza elementele in descrestere de la pozitia 6 pana la 1
print(x[6 :1])
#elimina elementele de la pozitia 1 pana la 4
print(x[-(1 : 4)])
#elimina elementele de pe pozitia 1 si 4
print(x[-c(1 , 4)])  
