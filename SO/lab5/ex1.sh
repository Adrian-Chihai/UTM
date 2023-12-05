#!/bin/bash 
output_ps=$(ps -u $USER -o pid,cmd --no-headers) 
echo "$output_ps" | awk '{print $1 ": " substr($0, index($0,$2))}' > procese.txt 
cat -n procese.txt
