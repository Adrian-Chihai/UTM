# Definim o funcție numită "process_data_and_plot" care primește un singur argument, "file_path", reprezentând calea către fișierul de date.
process_data_and_plot <- function(file_path) {
  # Citim datele din fișierul specificat într-un obiect de tip data frame numit "dataF".
  dataF <- read.table(file = file_path, header = TRUE)
  
  # Afișăm tabelul de date citit.
  print(dataF)
  
  # Transpunem datele din "dataF" folosind funcția "t" și le stocăm în "transposed_data".
  transposed_data <- t(dataF)
  
  # Generăm un grafic liniar pentru datele transpuse folosind funcția "matplot".
  # "type = "l"" specifică tipul de grafic (liniar), iar "main" setează titlul graficului.
  matplot(transposed_data, type = "l", main = "Plot fisier data.txt")
}

# Specificăm calea către fișierul de date pe care dorim să-l procesăm și să-l afișăm sub formă de grafic.
file_path <- "D:/UNIVER/ANUL_3/PD/LAB_1/PD_LAB_1/data.txt"

# Apelăm funcția "process_data_and_plot" cu calea către fișierul de date ca argument.
process_data_and_plot(file_path)

