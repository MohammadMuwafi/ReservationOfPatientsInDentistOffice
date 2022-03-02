DROP DATABASE finalProject;
CREATE DATABASE finalProject;
SHOW DATABASES;
USE finalProject;

-- doctor table
CREATE TABLE doctor (
	dname VARCHAR(50) NOT NULL DEFAULT 'Unknown',
    dnumber VARCHAR(50) NOT NULL DEFAULT '0595150676',
    clinic VARCHAR(50) NOT NULL DEFAULT 'Unknown',
    demail VARCHAR(50) PRIMARY KEY NOT NULL
    );
    
INSERT INTO doctor VALUES 
('ali', '0123456789', 'clinic1', 'ali@gmail.com'),
('ahmed', '0234567895', 'clinic2', 'ahmed@gmail.com'),
('basem', '0345678951', 'clinic3', 'basem@gmail.com'),
('essa', '0456789512', 'clinic4', 'essa@gmail.com'),
('mousa', '0567895123', 'clinic5', 'mousa@gmail.com'),
('khalil', '0678951234', 'clinic6', 'khalil@gmail.com'),
('khalid', '0789512345', 'clinic7', 'khalid@gmail.com'),
('abd', '0895123456', 'clinic8', 'abd@gmail.com'),
('mohammad', '0912345678', 'clinic9', 'mohammad@gmail.com');

-- patient table
CREATE TABLE patient (
	pid INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
    pname VARCHAR(50) NOT NULL,
    pgender VARCHAR(50) NOT NULL,
    demail VARCHAR(50) NOT NULL,
    pcost VARCHAR(50) DEFAULT '50',
    pbirthDate DATE DEFAULT '1990-01-01', -- 'YYYY-MM-DD'
	pdateOfExamination DATE NOT NULL DEFAULT '1990-01-01',
    ptimeOfdayOfExamination VARCHAR(50) NOT NULL DEFAULT '0',
	pnumberOfteeth VARCHAR(50) NOT NULL DEFAULT '0',
	pcolorOfMaterial VARCHAR(50) NOT NULL DEFAULT '0',
    pTypeOfMaterial VARCHAR(50) NOT NULL DEFAULT '0',
    FOREIGN KEY(demail) REFERENCES doctor(demail)
    );

DELIMITER $$
CREATE PROCEDURE insertPatient(
	IN ID INT, patientName VARCHAR(255), doctorEmail VARCHAR(255)
)
BEGIN
	DECLARE x INT;
	DECLARE cnt INT;
	DECLARE str VARCHAR(255);
	SET str = CONCAT(patientName, '', '');
	SELECT str;
	SET cnt = 1;
    SET x = ID;
	LOOP_LABEL:  LOOP
		IF  cnt > 99 THEN 
			LEAVE  LOOP_LABEL;
		END  IF;
		SET  x = x + 1;
		SET  cnt = cnt + 1;
		SET  str = CONCAT(patientName, x, '');
		INSERT INTO patient (pid, pname, pgender, demail) VALUES(x, str, 'male', doctorEmail);
	END LOOP;
END$$
DELIMITER ;

CALL insertPatient(0, "Mousa", 'ali@gmail.com');
CALL insertPatient(100, 'ahmed', 'ali@gmail.com');
CALL insertPatient(200, 'mohammad', 'ahmed@gmail.com');
CALL insertPatient(300, 'hamed', 'basem@gmail.com');
CALL insertPatient(400, 'mahmoud', 'essa@gmail.com');
CALL insertPatient(500, 'momen', 'mousa@gmail.com');
CALL insertPatient(600, 'baraa', 'khalil@gmail.com');
CALL insertPatient(700, 'yousef', 'khalid@gmail.com');
CALL insertPatient(800, 'essa', 'abd@gmail.com');
CALL insertPatient(900, 'abd', 'mohammad@gmail.com');

SELECT * FROM patient;