-- de afisat numele si prenumele primilor 5 studenti care au
-- obtinut note in ordine descrescatoarea la al doilea test 
SELECT studenti.nume_student, studenti.prenume_student
FROM studenti
JOIN studenti_reusita ON studenti.id_student = studenti_reusita.id_student

WHERE studenti_reusita.tip_evaluare = 'Evaluarea2'
    AND studenti_reusita.id_disciplina = 30

ORDER BY studenti_reusita.nota DESC

LIMIT 5;
