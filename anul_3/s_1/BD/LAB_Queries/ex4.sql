--in ce grupe figureaza mai mult de 24 de studenti
SELECT cod_grupa
FROM (
    SELECT cod_grupa, COUNT(DISTINCT id_student) AS c
    FROM studenti_reusita
    JOIN grupe g ON g.id_grupa = studenti_reusita.id_grupa
    GROUP BY cod_grupa, id_student
)
GROUP BY cod_grupa
HAVING COUNT(c) > 10;
