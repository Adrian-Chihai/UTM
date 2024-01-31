#!/bin/fish

# Directorul în care se află fișierele de informații despre procese (/proc)
set proc_dir "/proc"

# Fișierul în care vom scrie rezultatele
set output_file "rezultate.txt"

# Cautăm toate subdirectoarele în /proc care reprezintă procese
for pid_dir in $proc_dir/[0-9]*
    set pid (basename "$pid_dir")

    # Verificăm dacă fișierul status există pentru procesul curent
    set status_file "$pid_dir/status"
    if test -f "$status_file"
        # Extragem informațiile necesare din fișierul status
        set ppid (grep 'PPid' "$status_file" | awk '{print $2}')
        set sum_exec_runtime (grep 'se.sum_exec_runtime' "$status_file" | awk '{print $2}')
        set nr_switches (grep 'nr_switches' "$status_file" | awk '{print $2}')

        # Calculăm avg_atom
        if test "$nr_switches" -ne 0
            set avg_atom (math "$sum_exec_runtime" / "$nr_switches")
        else
            set avg_atom 0
        end

        # Adăugăm informațiile într-un fișier
        echo "ProcessID=$pid:Parent_ProcessID=$ppid:Average_Time=$avg_atom" >> "$output_file"
    end
end

# Sortăm rezultatele după identificatorii proceselor părinte (PPID)
sort -t "=" -k4 -n -o "$output_file"

# Afișăm rezultatele
echo "Rezultatele au fost salvate în $output_file"
cat "$output_file"

