size <- c(178, 175, 160, 191, 176, 155, 163, 174, 182)
print(size)
size_1 <- c(164, 172, 156, 195, 166)
print(size_1)
new.size <- c(rep(size_1, times = 2), tail(size, 7))
print(new.size)
write.csv(new.size, file = "D:/UNIVER/ANUL_3/PD/LAB_2/ex6.csv")

