-- Prima interogare
SELECT nume_student, prenume_student
FROM studenti_reusita
JOIN studenti ON studenti.id_student = studenti_reusita.id_student
JOIN profesori ON profesori.id_profesor = studenti_reusita.id_profesor
WHERE tip_evaluare = 'Examen'
    AND prenume_profesor = 'Ion'
    AND EXTRACT(YEAR FROM data_evaluare) = 2017

UNION

-- A doua interogare
SELECT DISTINCT nume_student, prenume_student
FROM studenti_reusita
JOIN studenti ON studenti.id_student = studenti_reusita.id_student
JOIN profesori ON profesori.id_profesor = studenti_reusita.id_profesor
WHERE tip_evaluare = 'Examen'
    AND prenume_profesor = 'George'
    AND EXTRACT(YEAR FROM data_evaluare) = 2017;
