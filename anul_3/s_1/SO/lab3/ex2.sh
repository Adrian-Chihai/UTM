#!/bin/bash
echo "Introdu nr1"
read nr1

echo "Introdu nr2"
read nr2

echo "Introdu nr3"
read nr3

if [ "$nr1" -gt "$nr2" ] && [ "$nr1" -gt "$nr3" ] 
then
	echo "Nr $nr1 este cel mai mare"
elif [ "$nr2" -gt "$nr1" ] && [ "$nr2" -gt "$nr3" ]
then
	echo "Nr $nr2 este cel mai mare"
else
	echo "Nr $nr3 este cel mai mare"
fi

