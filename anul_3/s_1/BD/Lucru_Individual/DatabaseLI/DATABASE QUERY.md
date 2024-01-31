CREATE DATABASE Persoane

CREATE TABLE Categorii(
	CategorieSocioProfesionalaID SERIAL PRIMARY KEY,
	DenumireCategorie varchar(100)
);

CREATE TABLE Intrebari(
	IntrebareID SERIAL PRIMARY KEY,
	DescriereIntrebare TEXT
);

CREATE TABLE Persoane(
	PersoanaID SERIAL PRIMARY KEY,
	Varsta INT,
	Sex varchar(50),
	CategorieSocioProfesionalaID INT,
	FOREIGN KEY (CategorieSocioProfesionalaID) REFERENCES Categorii(CategorieSocioProfesionalaID)
);

CREATE TABLE Raspunsuri(
	RaspunsID SERIAL PRIMARY KEY,
	IntrebareID INT,
	PersoanaID INT,
	Raspunsuri TEXT,
	FOREIGN KEY (IntrebareID) REFERENCES Intrebari(IntrebareID),
	FOREIGN KEY (PersoanaID) REFERENCES Persoane(PersoanaID)
);

--relatie 1 la 1 cu Persoane
CREATE TABLE Contact_Persoane (
    ContactInfoID SERIAL PRIMARY KEY,
    PersoanaID INT UNIQUE,
    Email VARCHAR(100),
    NrTelefon VARCHAR(20),
    FOREIGN KEY (PersoanaID) REFERENCES Persoane(PersoanaID)
);

