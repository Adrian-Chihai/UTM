process_data_and_plot <- function(file_path) {
  dataF <- read.table(file = file_path, header = TRUE)
  
  #printeaza tabelul
  print(dataF)
  
  #Transpune datele
  transposed_data <- t(dataF)
  
  #Genereaza un grafic pentru datele transpuse folosind matplot
  matplot(transposed_data, type = "l", main = "Plot fisier data.txt")
}

file_path <- "D:/UNIVER/ANUL_3/PD/LAB_1/PD_LAB_1/data.txt"
process_data_and_plot(file_path)

