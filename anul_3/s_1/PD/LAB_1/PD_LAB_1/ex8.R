airquality <- datasets::airquality

#Ozone: Concentrația de ozon în aer (în unități Dobson).
#Solar.R: Radiația solară (în langleys).
#Wind: Viteza vântului (în mile pe oră).
#Temp: Temperatura (în grade Fahrenheit).
#Month: Luna (1-12).
#Day: Ziua (1-31).
summary(airquality)
media_temp <- mean(airquality$Temp)
mediana_temp <- median(airquality$Temp)
deviatia_std_temp <- sd(airquality$Temp)

print(paste("Media Temp: ", media_temp))
print(paste("Mediana Temp: ", mediana_temp))
print(paste("Deviatia Standard Temp: ", deviatia_std_temp))

varianța_temp <- var(airquality$Temp)
deviatia_std_custom <- function(x) sqrt(var(x))

print(paste("Varianța Temp: ", varianța_temp))
print(paste("Deviatia Standard (utilizând funcția personalizată): ", deviatia_std_custom(airquality$Temp)))

a_doua_linie <- airquality[2, ]
print(a_doua_linie)

a_treia_coloana <- airquality[, 3]
print(a_treia_coloana)

liniile_124 <- airquality[c(1, 2, 4), ]
print(liniile_124)

liniile_26 <- airquality[2:6, ]
print(liniile_26)


toate_exceptie_12 <- airquality[, -c(1, 2)]
print(toate_exceptie_12)

toate_liniile_temp_mai_mare_de_90 <- airquality[airquality$Temp > 90, ]
print(toate_liniile_temp_mai_mare_de_90)

