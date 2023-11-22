SELECT disciplina, average
FROM (
    SELECT id_disciplina, AVG(CAST(nota AS FLOAT)) AS average
    FROM (
        SELECT id_disciplina, nota
        FROM studenti_reusita
        GROUP BY id_disciplina, nota, tip_evaluare
    ) t
    GROUP BY id_disciplina
) u
JOIN discipline ON u.id_disciplina = discipline.id_disciplina
WHERE average > 7.0;
