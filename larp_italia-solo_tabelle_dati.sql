CREATE TABLE DIPENDENTE(
	CF VARCHAR(15) PRIMARY KEY,
	CELLULARE VARCHAR(15) NOT NULL,
	INDIRIZZO VARCHAR(63) NOT NULL,
	NOME VARCHAR(63) NOT NULL,
	COGNOME VARCHAR(63) NOT NULL,
	STIPENDIO INTEGER NOT NULL
);
CREATE TABLE GIOCATORE(
	CF VARCHAR(15) PRIMARY KEY,
	CELLULARE VARCHAR(15) NOT NULL,
	INDIRIZZO VARCHAR(63) NOT NULL,
	NOME VARCHAR(63) NOT NULL,
	COGNOME VARCHAR(63) NOT NULL,
	EMAIL VARCHAR(63) NOT NULL
);

CREATE TABLE MONDO(
	NOME VARCHAR(15) PRIMARY KEY,
	TIPO VARCHAR(31)
);


CREATE TABLE CAMPAGNA(
	NOME VARCHAR(63) PRIMARY KEY,
	DESCRIZIONE VARCHAR(255) NOT NULL,
	ANNO_DI_PARTENZA INTEGER NOT NULL,
	NOME_MONDO VARCHAR(15) NOT NULL,
	ONESHOOT BOOLEAN DEFAULT FALSE NOT NULL,
    FOREIGN KEY(NOME_MONDO) REFERENCES MONDO(NOME)

);
CREATE TABLE EVENTO(
	TITOLO VARCHAR(63) PRIMARY KEY,
	NOME_CAMPAGNA VARCHAR(15) NOT NULL,
	DATA_INIZIO DATE NOT NULL CHECK(DATA_INIZIO >= NOW()),
	DATA_FINE DATE NOT NULL CHECK(DATA_FINE >= NOW()),
	LIMITE_PARTECIPANTI INTEGER,
	COSTO_ISCRIZIONE INTEGER DEFAULT 0,
	INDIRIZZO_LOCATION VARCHAR(63) NOT NULL,
	FOREIGN KEY (NOME_CAMPAGNA) REFERENCES CAMPAGNA(NOME),
	CHECK(DATA_INIZIO<=DATA_FINE)
);

CREATE TABLE NPC(
	COD_NPC SERIAL PRIMARY KEY,
	NOME VARCHAR(31) NOT NULL,
	DESCRIZIONE VARCHAR(255) NOT NULL
);

CREATE TABLE PERSONAGGIO(
	COD_PG SERIAL PRIMARY KEY,
	NOME VARCHAR(31) NOT NULL,
	DESCRIZIONE VARCHAR(255) NOT NULL,
	EXP INTEGER DEFAULT 2600 NOT NULL,-- Exp DI DEFAULT ALLA CREAZIONE DEL PERSONAGGIO
	TIPO VARCHAR(31)
);
CREATE TABLE MISSIONE(
	COD_MISSIONE SERIAL PRIMARY KEY, 
	TITOLO VARCHAR(31) NOT NULL,
	GUADAGNO_EXP INTEGER DEFAULT 0,
	FALLITA BOOLEAN DEFAULT NULL -- NULL = IN CORSO
);

CREATE TABLE INTERPRETA(
	CF VARCHAR(15) NOT NULL,
	TITOLO VARCHAR(63) NOT NULL,
	TURNO SMALLINT NOT NULL CHECK(TURNO BETWEEN 1 AND 7),
	COD_NPC INTEGER NOT NULL,
	PRIMARY KEY(CF,TITOLO),
	FOREIGN KEY(CF) REFERENCES DIPENDENTE(CF),
	FOREIGN KEY(TITOLO) REFERENCES EVENTO(TITOLO) ON DELETE CASCADE,
	FOREIGN KEY(COD_NPC) REFERENCES NPC(COD_NPC)
);
CREATE TABLE PARTECIPA(
	CF VARCHAR(15) NOT NULL,
	TITOLO VARCHAR(63) NOT NULL,
	NRO_TICKET SERIAL,
	COD_PG INTEGER NOT NULL,
	PRIMARY KEY(CF,TITOLO),
	FOREIGN KEY(CF) REFERENCES GIOCATORE(CF),
	FOREIGN KEY(TITOLO) REFERENCES EVENTO(TITOLO) ON DELETE CASCADE,
	FOREIGN KEY(COD_PG) REFERENCES PERSONAGGIO(COD_PG)
);
CREATE TABLE AVVENIMENTO(
	COD_AVV SERIAL PRIMARY KEY,
	NOME_MONDO VARCHAR(15) NOT NULL,
	DESCRIZIONE VARCHAR(255) NOT NULL,
	"DATA" VARCHAR(15) NOT NULL,
	FOREIGN KEY(NOME_MONDO) REFERENCES MONDO(NOME) 
	ON DELETE CASCADE
);
CREATE TABLE GENERA(
	COD_AVV INTEGER,
	COD_MISSIONE INTEGER,
	PRIMARY KEY(COD_AVV,COD_MISSIONE),
	FOREIGN KEY(COD_AVV) REFERENCES AVVENIMENTO(COD_AVV)
	ON DELETE CASCADE,
	FOREIGN KEY(COD_MISSIONE) REFERENCES MISSIONE(COD_MISSIONE)
	ON DELETE CASCADE
);
CREATE TABLE INTRAPRENDE(
	COD_PG INTEGER NOT NULL,
	COD_MISSIONE INTEGER NOT NULL,
	PRIMARY KEY(COD_PG,COD_MISSIONE),
	FOREIGN KEY(COD_MISSIONE) REFERENCES MISSIONE(COD_MISSIONE) ON DELETE CASCADE,
	FOREIGN KEY(COD_PG) REFERENCES PERSONAGGIO(COD_PG)
);
CREATE TABLE ASSEGNA(
	COD_NPC INTEGER NOT NULL,
	COD_MISSIONE INTEGER NOT NULL,
	PRIMARY KEY(COD_NPC,COD_MISSIONE),
	FOREIGN KEY(COD_MISSIONE) REFERENCES MISSIONE(COD_MISSIONE) ON DELETE CASCADE,
	FOREIGN KEY(COD_NPC) REFERENCES NPC(COD_NPC) ON DELETE CASCADE
);

CREATE TABLE OGGETTO(
	NOME VARCHAR(31) NOT NULL,
	COD_PG INTEGER NOT NULL,
	DESCRIZIONE VARCHAR(255) NOT NULL,
	VALORE INTEGER DEFAULT 0 NOT NULL,
	QUANTITA INTEGER DEFAULT 1 NOT NULL,
	PUNTI_ATTACCO INTEGER DEFAULT 0 NOT NULL,
	PUNTI_DIFESA INTEGER DEFAULT 0 NOT NULL,
	PRIMARY KEY(NOME,COD_PG),
	FOREIGN KEY(COD_PG) REFERENCES PERSONAGGIO(COD_PG) ON DELETE CASCADE 
);


CREATE TABLE ABILITA(
	NOME VARCHAR(31) NOT NULL,
	COD_PG INTEGER NOT NULL,
	DESCRIZIONE VARCHAR(255) NOT NULL,
	RESTRIZIONE VARCHAR(15),
	ABILITA_PADRE VARCHAR(31),
	COSTO_EXP INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY(NOME,COD_PG), -- KEY COMPOSTA
	FOREIGN KEY(COD_PG) REFERENCES PERSONAGGIO(COD_PG) ON DELETE CASCADE, -- REALZIONATO AL PERSONAGGIO
	FOREIGN KEY(ABILITA_PADRE,COD_PG) REFERENCES ABILITA(NOME,COD_PG) ON DELETE CASCADE --SE PERDE UNA ABILITA QUELLE FIGLIE MUOIONO
);

-- TRIGGER E STORED PROCEDURE
CREATE FUNCTION ISCRIZIONE(CF VARCHAR(15), EVENTO VARCHAR(63), COD_PG INTEGER) RETURNS INTEGER AS $$
DECLARE
 LIM INTEGER;
 TOT INTEGER;
 NRO_TICKET INTEGER;
BEGIN
    SELECT E.LIMITE_PARTECIPANTI INTO LIM FROM EVENTO AS E WHERE E.TITOLO = EVENTO;
	SELECT COUNT(*) INTO TOT FROM PARTECIPA AS P WHERE P.TITOLO = EVENTO;
	NRO_TICKET := TOT+1;
	
	IF LIM < TOT + 1  THEN
	  RAISE EXCEPTION 'LIMITE PARTECIPANTI RAGGIUNTO';
	END IF;
	
	INSERT INTO PARTECIPA(cf,titolo,cod_pg,nro_ticket) VALUES(CF,EVENTO,COD_PG,NRO_TICKET);
	
	RETURN NRO_TICKET;
END;
$$ LANGUAGE 'plpgsql';
-- ESEMPIO DI CHIAMATA
-- SELECT ISCRIZIONE('CD124','Gli antenati perduti');

-- DATI
INSERT INTO DIPENDENTE 
VALUES
('CF34','+3998676556','Via dei boschi 17','luca','longagnani',1200),
('CF89','+3998987087','Via dei ragazzi del 99','elena','vita',1000),
('CF12','+3905987698','Via degli ulivi 199','margherita','pratice',1350),
('CF123','+395475475','Via dei partori 47','francesco','rugolo',890);

INSERT INTO GIOCATORE 
VALUES
('CF1','+3923456','Via dei luogi 15','valentina','longo','foo@foo.it'),
('CD124','+31000329489','Via paolo 12','mario','rossi','bar@bar.it'),
('CH19','+3200293209','Via dei mario 16','giovanna','fusco','givo@gmail.com'),
('DF09','+3954728459','Via dei boschi 23','giovanni','fusco','pulcinella@infostrada.it'),
('CH1','+392350865123','Viale italia 189','stefano','amico','iphone@icloud.com'),
('KJ1','+3932747669988','Via dei marchi 12','giovanna','montanari','barone@gmail.com');

INSERT INTO MONDO(NOME,TIPO) 
VALUES
('Nirn','fantasy'),
('Pandora','fantascientifico');

INSERT INTO CAMPAGNA(NOME,DESCRIZIONE,ANNO_DI_PARTENZA,NOME_MONDO, ONESHOOT)
VALUES
('Avatar','Una campagna ricca di azione nei panni di alieni giganteschi che si trovano a lottare per il proprio pianeta.',4600,'Pandora',true),
('Skyrim','Una avventura senza paragoni, in un mondo di eroi, draghi e non morti da sconfiggere.',568,'Nirn',false);

INSERT INTO EVENTO(TITOLO,NOME_CAMPAGNA,DATA_INIZIO,DATA_FINE,LIMITE_PARTECIPANTI,COSTO_ISCRIZIONE,INDIRIZZO_LOCATION)
VALUES
('Gli antenati perduti','Avatar',to_date('2022-0104', 'YYYY-MMDD'),to_date('2022-0104', 'YYYY-MMDD'),20,20,'Via dei mille 99, San Giuliano PI'),
('Il nuovo imperatore','Skyrim',to_date('2022-1120', 'YYYY-MMDD'),to_date('2022-1123', 'YYYY-MMDD'),null,60,'Pascoli albini presso villa de Paoli, Milano MI');

INSERT INTO NPC(NOME,DESCRIZIONE)
VALUES
('Grace Augustine','Dottoressa che studia gli abitanti ed i biomi del pianete Pandora.'),
('Jake Sully','Giovane soldato costretto ad una sedia a rotelle per colpa di un incidente in guerra.'),
('Azura','Azura è il principe daedrico del tramonto e dell alba e uno dei pochi a non essere considerato intrinsecamente malvagio.'),
('Sanguine','Sanguine è il principe daedrico dell edonismo, della dissolutezza e delle oscure indulgenze. È più probabile che la maggior parte dei principi si interessi agli affari mortali.');

INSERT INTO INTERPRETA(CF,TITOLO,TURNO,COD_NPC)
VALUES
('CF34','Gli antenati perduti',1,1),
('CF89','Gli antenati perduti',1,2),
('CF12','Il nuovo imperatore',1,3),
('CF123','Il nuovo imperatore',1,4);

insert into personaggio(nome,descrizione,tipo)
values
-- skyrim
('Pascol','Soldato di razza imperiale abile nel combattimento corpo a corpo.','guerriero'),
('Rinocy','Elfo oscuro nato per castare gli incantesimi più forti.','mago'),
('Lane mart','Giovane orco lasciato orfano dalle guerre.', null),
('Loroc','Bretone visto ed allegro un abile contadino.',null),
('Xarad','Un Khajiiti nomade che ha perso il proprio gruppo ed è alla ricerca di uno nuovo.','ladro'),
('Xyvir Amen','Una nobile elfa alta abile con arco e frecce.','ranger'),
-- avatar oneshoot
('Xoror Iomor','Anziano guerrioro che lotta per la propria casa.','guerriero'),
('Xyam Umridad','Giovane Navi asperto nella caccia.','ranger'),
('Jhon wine','Guerriero scelto abile nel cambattimento con robot da guerra.','mech'),
('Dottor Much','Dottore che studia la razza Navi sul pianeta pandora.',null);


--ISCRIZIONE PRIMO EVENTO SKYRIM
SELECT ISCRIZIONE('CF1','Il nuovo imperatore',1);
SELECT ISCRIZIONE('CD124','Il nuovo imperatore',2);
SELECT ISCRIZIONE('CH19','Il nuovo imperatore',3);
SELECT ISCRIZIONE('DF09','Il nuovo imperatore',4);
SELECT ISCRIZIONE('CH1','Il nuovo imperatore',5);
SELECT ISCRIZIONE('KJ1','Il nuovo imperatore',6);
--ISCRIZIONE EVENTO ONESHOOT AVATAR
SELECT ISCRIZIONE('CF1','Gli antenati perduti',7);
SELECT ISCRIZIONE('CH19','Gli antenati perduti',8);
SELECT ISCRIZIONE('DF09','Gli antenati perduti',9);
SELECT ISCRIZIONE('KJ1','Gli antenati perduti',10);

--insert into partecipa(cf,titolo,cod_pg,nro_ticket)
--values
-- skyrim
--('CF1','Il nuovo imperatore',1,1),
--('CD124','Il nuovo imperatore',2,2),
--('CH19','Il nuovo imperatore',3,3),
--('DF09','Il nuovo imperatore',4,4),
--('CH1','Il nuovo imperatore',5,5),
--('KJ1','Il nuovo imperatore',6,6),
-- avatar oneshoot
--('CF1','Gli antenati perduti',7,1),
--('CH19','Gli antenati perduti',8,2),
--('DF09','Gli antenati perduti',9,3),
--('KJ1','Gli antenati perduti',10,4);

insert into missione(titolo,guadagno_exp)
values
('prima missione',150),
('seconda missione',150),
('terza missione',320),
('quarta missione',1200);

insert into intraprende(cod_pg,cod_missione)
values
(1,1),
(10,2),
(2,1),
(3,1),
(4,2),
(5,3),
(6,4),
(7,3),
(8,4),
(9,4);

insert into avvenimento(nome_mondo,descrizione,"DATA")
values
('Nirn', 'Il mago Rinocy diventa arcimago.','12-12-4600'),
('Pandora','Gli avatar vincono la battaglia.','03-01-568');

insert into abilita(nome,cod_pg, descrizione, restrizione, abilita_padre, costo_exp)
values  
('armi corte', 1, 'Abilità che permette di utilizzare armi corte.',null,null,800),
('armi lunghe', 1, 'Abilità che permette di utilizzare armi lunghe.','guerriero','armi corte',800),
('magia', 2, 'Abilità che permette di utilizzare la magia.','mago',null,1200),
('palla di fuoco', 2, 'Abilità che permette di utilizzare la magia palla di fuoco.','mago','magia',600),
('furtività', 5, 'Abilità che permette di muoversi senza farsi sentire.','ladro',null,900),
('armi da distanza', 6, 'Abilità che permette di utilizzare meglio archi lunghi e corti.','ranger',null,1200),
('armature leggere', 7, 'Abilità che permette di utilizzare armature leggere.',null,null,1000),
('armature pesanti', 7, 'Abilità che permette di utilizzare armature pesanti.','guerriero','armature leggere',1600),
('macchine leggere', 9, 'Abilità che permette di utilizzare macchine da guerra leggere.','mech',null,1000),
('macchine pesanti', 9, 'Abilità che permette di utilizzare macchine da guerra pesanti.','mech','macchine leggere',1600);

insert into oggetto(nome, cod_pg, valore, quantita, descrizione, punti_attacco, punti_difesa)
values
('pugnale',1,2,1,'pugnale corto ed affilato.',3,0),
('pugnale',2,2,1,'pugnale corto ed affilato.',3,0),
('pugnale',3,2,1,'pugnale corto ed affilato.',3,0),
('pugnale',4,2,1,'pugnale corto ed affilato.',3,0),
('pugnale',7,2,1,'pugnale corto ed affilato.',3,0),
('pugnale da guerriero',8,15,1,'pugnale Lungo ed ben affilato.',7,1),
('bastone magico',2,50,1,'bastone magico.',20,0),
('cappa',2,5,1,'cappa per proteggersi dal sole.',0,3),
('corpetto leggero',5,10,1,'corpetto protettivo.',0,10),
('armatura in maglie leggera',6,20,1,'armatura leggera per arceri e cacciatori.',0,15),
('Ascia affilata',1,170,1,'Ascia adatta ad ogni evenienza.',65,0),
('arco corto',6,2,1,'arco corto e frecce.',10,0),
('pugnale',10,2,1,'pugnale corto ed affilato.',3,0),
('Macchina xYp01',9,20000,1,'Macchinario da guerra pesante adatto contro ogni bestia che ospita pandora.',240,240),
('Siringhe di veleno',10,150,10,'siringhe con del veleno.',35,0),
('Spada corta',7,25,1,'spada corta arrugginita.',15,0),
('Armatura pesante in ferro',7,200,1,'armatura per veri guerrieri.',0,30),
('cotta di maglia',8,25,1,'una armatura leggera e ben decorata.',0,15),
('arco lungo',8,120,1,'arco lungo e frecce.',0,50),
('Veste da contadino',4,5,1,'veste che offre protezione dal sole cocente.',0,5);

insert into assegna values
(1,3),
(2,4),
(3,1),
(4,2);

insert into genera values 
(1,1),
(2,4);



-- ANCORA NON FUNZIONA NON LO COPIARE

CREATE TRIGGER MISSIONE_COMPLETATA
AFTER INSERT OR UPDATE OF FALLITA ON MISSIONE
WHEN(OLD.FALLITA = FALSE) --MISSIONE COMPLETATA
EXECUTE PROCEDURE UPDATE_USERS_EXP(NEW.COD_MISSIONE);


CREATE FUNCTION UPDATE_USERS_EXP(COD_MISSIONE INTEGER) RETURNS INTEGER AS $$
DECLARE
 PARTECIPANTI INTEGER;
 PAR_EXP INTEGER;
BEGIN

	SELECT COUNT(*) INTO PARTECIPANTI FROM INTERPRETA AS I WHERE I.COD_MISISONE = COD_MISISONE;			 -- NUMERO DI PARTECIPANTI ALLA MISSIONE
    SELECT GUADAGNO_EXP/PARTECIPANTI INTO PAR_EXP FROM MISSIONE AS M WHERE M.COD_MISSIONE = COD_MISSIONE;-- SUDDIVIZIONE DELL'EXP FRA I PARTECIPANTI
	
	FOR R IN --FOR LOOP
		SELECT COD_PG INTO PAR_EXP FROM MISSIONE AS M WHERE M.COD_MISSIONE = COD_MISSIONE;
	LOOP
		UPDATE PERSONAGGIO SET EXP = EXP + PAR_EXP WHERE COD_PG = R.COD_PG
		RETURN NEXT R
	END LOOP;
	RETURN;
END;
$$ LANGUAGE 'plpgsql';