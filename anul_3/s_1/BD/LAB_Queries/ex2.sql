-- De gasit numele si prenumele studentilor 
-- care au dat examen la profesorul Ion
SELECT nume_student nume, prenume_student pren
FROM studenti_reusita
JOIN studenti ON studenti.id_student = studenti_reusita.id_student
JOIN profesori ON profesori.id_profesor = studenti_reusita.id_profesor
WHERE tip_evaluare = 'Examen'
    AND prenume_profesor = 'Ion'
    AND EXTRACT(YEAR FROM data_evaluare) = 2017

UNION

-- care au dat examen la profesorul George
SELECT nume_student, prenume_student
FROM studenti_reusita
JOIN studenti ON studenti.id_student = studenti_reusita.id_student
JOIN profesori ON profesori.id_profesor = studenti_reusita.id_profesor
WHERE tip_evaluare = 'Examen'
    AND prenume_profesor = 'George'
    AND EXTRACT(YEAR FROM data_evaluare) = 2017;
