-- Insert data into Categorii table
INSERT INTO Categorii (DenumireCategorie) VALUES 
    ('IT'),
    ('Educație'),
    ('Sănătate');

-- Insert data into Intrebari table
INSERT INTO Intrebari (DescriereIntrebare) VALUES 
    ('Care este capitala Japoniei?'),
    ('Cine a scris romanul "Mândrie și prejudecată"?'),
    ('Care este cel mai lung fluviu din lume?'),
    ('În ce an a avut loc Revoluția Franceză?');

-- Insert data into Persoane table
INSERT INTO Persoane (Varsta, Sex, CategorieSocioProfesionalaID) VALUES 
    (28, 'Masculin', 1),  -- Persoană în domeniul IT
    (35, 'Feminin', 2),   -- Profesor
    (40, 'Masculin', 3),  -- Medic
    (22, 'Feminin', 1);   -- Persoană în domeniul IT

-- Insert data into Raspunsuri table
INSERT INTO Raspunsuri (IntrebareID, PersoanaID, Raspunsuri) 
VALUES
    (1, 1, 'Tokyo'),      
    (1, 2, 'Osaka'),      
    (1, 3, 'Kyoto'),      
    (1, 4, 'Seoul'),      
    
    (2, 1, 'Jane Austen'),         
    (2, 2, 'Emily Brontë'),        
    (2, 3, 'F. Scott Fitzgerald'), 
    (2, 4, 'Leo Tolstoy'),         
    
    (3, 1, 'Nile'),     
    (3, 2, 'Nile'),   
    (3, 3, 'Nile'),     
    (3, 4, 'Mississippi'), 
    
    (4, 1, '1789'),   
    (4, 2, '1776'),   
    (4, 3, '1789'),   
    (4, 4, '1804');   


-- Insert data into Contact_Persoane table
INSERT INTO Contact_Persoane (PersoanaID, Email, NrTelefon) VALUES 
    (1, 'it_person@email.com', '1234567890'),
    (2, 'teacher@email.com', '9876543210'),
    (3, 'doctor@email.com', '5555555555'),
    (4, 'it_person2@email.com', '1111111111');
