USE AdventureWorks2022;

--interzicearea accesului la orice alt tabel din baza de date pentru acest utilizator
--DENY VIEW DEFINITION ON SCHEMA::PERSON TO VasileHR;
--DENY VIEW DEFINITION ON SCHEMA::Production TO VasileHR;
--DENY VIEW DEFINITION ON SCHEMA::Purchasing TO VasileHR;
--DENY VIEW DEFINITION ON SCHEMA::dbo TO VasileHR;
--DENY VIEW DEFINITION ON SCHEMA::Sales TO VasileHR;

--permiterea vizualizarii si modificarii datelor din tabelele HumanResources
--GRANT ALTER ON SCHEMA::HumanResources TO VasileHR;

--permiterea vizualizarii datelor din Sales fara modificarea lor