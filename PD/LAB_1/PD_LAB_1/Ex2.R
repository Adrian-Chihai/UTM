calcul_variantei <- function(x) {
  n <- length(x)
  
  if (n <= 1) {
    stop("Vectorul trebuie să aibă cel puțin două valori pentru a calcula varianța.")
  }
  
  mean_x <- mean(x)
  sum_squared_diff <- sum((x - mean_x)^2)
  
  variance <- sum_squared_diff / (n - 1)  # Utilizăm n - 1 pentru varianța eșantională
  
  return(variance)
}

#cream tabelul care va fi transmis ca argument
vector_date <- c(1, 2,3,4,5,6,7,78,43,9)
#apelam functia iar rezultatul ei il salvam in cadrul variabilei varianta
varianta <- calcul_variantei(vector_date)
#printam varianta
print(varianta)
