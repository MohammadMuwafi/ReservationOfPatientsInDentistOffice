drop database finalProject;
create database finalProject;
show databases;
use finalProject;

-- doctor table
create table doctor (
	dname varchar(50) not null default 'Unknown',
    dnumber varchar(50) not null default '0595150676',
    clinic varchar(50) not null default 'Unknown',
    demail varchar(50) primary key not null
    );
    
insert into doctor values 
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
create table patient (
	pid int primary key auto_increment not null, 
    pname varchar(50) not null,
    pgender varchar(50) not null,
    demail varchar(50) not null,
    pcost varchar(50) default '50',
    pbirthDate date default '1990-01-01', -- 'YYYY-MM-DD'
	pdateOfExamination date not null default '1990-01-01',
    ptimeOfdayOfExamination varchar(50) not null default '0',
	pnumberOfteeth varchar(50) not null default '0',
	pcolorOfMaterial varchar(50) not null default '0',
    pTypeOfMaterial varchar(50) not null default '0',
    foreign key(demail) references doctor(demail)
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
	select str;
	SET cnt = 1;
    SET x = ID;
	loop_label:  LOOP
		IF  cnt > 9 THEN 
			LEAVE  loop_label;
		END  IF;
		SET  x = x + 1;
		SET  cnt = cnt + 1;
		SET  str = CONCAT(patientName, x, '');
		INSERT INTO patient (pid, pname, pgender, demail) VALUES(x, str, 'male', doctorEmail);
	END LOOP;
END$$
DELIMITER ;

call insertPatient(0, "Mousa", 'ali@gmail.com');
call insertPatient(10, 'ahmed', 'ali@gmail.com');
call insertPatient(20, 'mohammad', 'ahmed@gmail.com');
call insertPatient(30, 'hamed', 'basem@gmail.com');
call insertPatient(40, 'mahmoud', 'essa@gmail.com');
call insertPatient(50, 'momen', 'mousa@gmail.com');
call insertPatient(60, 'baraa', 'khalil@gmail.com');
call insertPatient(70, 'yousef', 'khalid@gmail.com');
call insertPatient(80, 'essa', 'abd@gmail.com');
call insertPatient(90, 'abd', 'mohammad@gmail.com');

select * from patient;