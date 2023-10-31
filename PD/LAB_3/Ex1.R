meanVector <- function(arr){
  media <- mean(arr)
  media_4cifre <- round(media, 4)
  return(media_4cifre)
} 

arr <- c(13, 4.343, 434, 32, 34.54, 32)
res <- meanVector(arr)
print(res)