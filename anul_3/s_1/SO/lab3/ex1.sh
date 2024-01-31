#!/bin/bash
echo "Introdu sirul 1"
read sir1

echo "Introdu sirul 2"
read sir2

if [ "$sir1" == "$sir2" ]
then
       	echo "Sunt egal cele 2 siruri" 
else 
	echo "Nu sunt egale cele 2 siruri" 
fi
