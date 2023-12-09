# Extrage PID-ul și timpul de lansare utilizând awk
pid=$(echo $ultimul_proces | awk '{print $1}')
timp_lansare=$(echo $ultimul_proces | awk '{print $2}')

# Afișează rezultatele
echo "PID-ul ultimului proces: $pid"
echo "Timpul de lansare (doar ora): $(date -d "$timp_lansare" '+%H %M %S')"

