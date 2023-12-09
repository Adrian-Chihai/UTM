#!/bin/bash
set script_path="/home/h1p/UTM/OS/Lab_6/Task1.sh"
mkdir -p ~/test
if test $status -eq 0
    echo "catalog test was created successfully" >> ~/UTM/OS/Lab_6/raport
    date '+%Y-%m-%d_%H-%M-%S' >> ~/test/Data_Ora_Lansarii_Scriptului
end

ping -c 1 www.traiasca_moldova.md 

if test $status -eq 0
    echo "Host-ul www.traiasca_moldova.md este disponibil." >> ~/UTM/OS/Lab_6/raport
else
    echo "Host-ul www.traiasca_moldova.md nu este disponibil." >> ~/UTM/OS/Lab_6/raport
end
#Se ruleaza dupa 2 minute 
#echo "~/UTM/OS/Lab_6/Task1.sh"|at now + 2 minutes
#Se ruleaza in fiecare 5 min din ora 
#(crontab -l ; echo */5 * */2 * * /bin/bash /home/h1p/UTM/OS/Lab_6/Task1.sh >/dev/null 2>&1) | crontab -
