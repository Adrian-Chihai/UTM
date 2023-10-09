# Definim o funcție numită "calcul_variantei" care primește un vector x ca argument.
calcul_variantei <- function(x) {
  # Calculăm lungimea vectorului x și o stocăm în variabila n.
  n <- length(x)
  
  # Verificăm dacă vectorul are cel puțin două valori pentru a calcula varianța.
  if (n <= 1) {
    stop("Vectorul trebuie să aibă cel puțin două valori pentru a calcula varianța.")
  }
  
  # Calculăm media (media aritmetică) a vectorului x și o stocăm în variabila mean_x.
  mean_x <- mean(x)
  
  # Calculăm suma pătratelor diferențelor dintre fiecare valoare din x și media x.
  sum_squared_diff <- sum((x - mean_x)^2)
  
  # Calculăm varianța utilizând formula varianței eșantionale (cu n - 1 în numitor).
  variance <- sum_squared_diff / (n - 1)
  
  # Returnăm valoarea varianței calculată.
  return(variance)
}

# Cream un vector de date numit "vector_date".
vector_date <- c(1, 2, 3, 4, 5, 6, 7, 78, 43, 9)

# Apelăm funcția "calcul_variantei" cu "vector_date" ca argument și salvăm rezultatul în variabila "varianta".
varianta <- calcul_variantei(vector_date)

# Afișăm valoarea rezultată a varianței.
print(paste("Varianta vectorului este -> " , varianta))
