#!/bin/bash

proc_dir="/proc"
output_file="output_file.txt"

for pid_dir in $proc_dir/[0-9]*/; do
    pid=$(basename "$pid_dir")

    status_file="$pid_dir/status"
    if [ -f "$status_file" ]; then
        ppid=$(awk '/PPid/ {print $2}' "$status_file")
        sum_exec_runtime=$(awk '/se.sum_exec_runtime/ {print $2}' "$status_file")
        nr_switches=$(awk '/nr_switches/ {print $2}' "$status_file")

        # Verificare pentru a asigura că variabilele esențiale sunt definite
        if [ -n "$pid" ] && [ -n "$ppid" ] && [ -n "$sum_exec_runtime" ] && [ -n "$nr_switches" ]; then
            if [ "$nr_switches" -ne 0 ]; then
                avg_atom=$((sum_exec_runtime / nr_switches))
            else
                avg_atom=0
            fi

            echo "ProcessID=$pid:Parent_ProcessID=$ppid:Average_Time=$avg_atom" >> "$output_file"
        else
            echo "Eroare: Nu s-au putut citi informațiile corecte pentru PID-ul $pid." >&2
        fi
    fi
done

