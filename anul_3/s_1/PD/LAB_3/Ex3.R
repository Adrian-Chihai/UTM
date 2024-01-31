vectorStats <- function(arr, option){
  if(option == "mean"){
    return(mean(arr))
  } else if(option == "median"){
    return(median(arr))
  } else if(option == "sd"){
    return(sd(arr))
  }
  return("No valid option")
}

arr <- c(4,3,5.4,34,34,43,43,32)
option = "mean"
res <- vectorStats(arr, option)
print(paste(option, " -> ", res))
option = "median"
res <- vectorStats(arr, option)
print(paste(option, " -> ", res))
option = "sd"
res <- vectorStats(arr, option)
print(paste(option, " -> ", res))