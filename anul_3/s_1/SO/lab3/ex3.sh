#!/bin/bash
echo "Acest program va rula pana vei apasa pe 'q'"
my_string=""
nr=0
while true; do
	read -n 1 char
	if [ "$char" == "q" ]
	then
		break
	fi
	my_string="${my_string}${char}"
	((nr++))
done

echo -e "\nAi introdus acest sir: $my_string"
echo -e "\nAre $nr caractere"
