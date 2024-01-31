A <- data.frame(
  Id = c(1,2,3,4,5),
  S = c("M","F","F","M","M"),
  W = c(75,68,48,72,83)
)

B <- data.frame(
  Id = c(1,2,3,4,5),
  S = c("M","F","F","M","M"),
  H = c(182,165,160,178,183)
)

combined_table <- cbind(A[c("Id", "S","W")], B[c("H")])

print(A)
print(B)
print(combined_table)
