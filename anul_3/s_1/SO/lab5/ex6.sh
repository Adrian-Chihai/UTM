#!/bin/bash

input_file="rezultate.txt"
output_file="output_file_modified.txt"

# Sortarea fișierului după PPID
sort -t= -k4,4n -k8,8n "$input_file" > "$output_file"

# Iterarea prin fișier și adăugarea liniei Sum_switches_of_ParentID
current_parent_id=""
sum_switches=0

while IFS= read -r line
do
    ppid=$(grep -oP 'Parent_ProcessID=\K\d+' <<< "$line")
    switches=$(grep -oP 'voluntary_ctxt_switches:\s+\K\d+' <<< "$line")

    if [ "$ppid" = "$current_parent_id" ]; then
        sum_switches=$((sum_switches + switches))
    else
        echo "Sum_switches_of_ParentID=$current_parent_id is $sum_switches" >> "$output_file"
        current_parent_id="$ppid"
        sum_switches="$switches"
    fi
done < "$output_file"

# Adăugarea ultimei linii pentru ultimul grup
echo "Sum_switches_of_ParentID=$current_parent_id is $sum_switches" >> "$output_file"

# Afișarea conținutului fișierului modificat
cat "$output_file"

