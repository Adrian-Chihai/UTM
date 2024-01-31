factLoop <- function(fact){
  res = 1
  for (i in 1:fact) {
    res = res * i
  }
  return(res)
}

factProd <- function(fact){
  res <- prod(1:fact)
  return(res)
}

print(factLoop(5))
print(factProd(5))
