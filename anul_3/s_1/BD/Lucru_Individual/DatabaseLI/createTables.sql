--1
CREATE TABLE categorii(
	categorieSocioProfesionalaID SERIAL PRIMARY KEY,
	denumireCategorie varchar(100)
);

--2
CREATE TABLE intrebari(
	intrebareID SERIAL PRIMARY KEY,
	descriereIntrebare TEXT
);

--3
CREATE TABLE persoane(
	persoanaID SERIAL PRIMARY KEY,
	varsta INT,
	sex varchar(50),
	categorieSocioProfesionalaID INT,
	FOREIGN KEY (categorieSocioProfesionalaID) REFERENCES categorii(categorieSocioProfesionalaID)
);

--4
CREATE TABLE raspunsuri(
	raspunsID SERIAL PRIMARY KEY,
	intrebareID INT,
	persoanaID INT,
	raspunsuri TEXT,
	FOREIGN KEY (intrebareID) REFERENCES intrebari(intrebareID),
	FOREIGN KEY (persoanaID) REFERENCES persoane(persoanaID)
);
