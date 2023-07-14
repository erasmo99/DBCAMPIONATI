create database Campionati;
use Campionati;

create table Campionato
	( 	nome varchar(50)NOT NULL,
		nazione varchar(50) NOT NULL,
		primary key(nome));
create table Squadra
	(	nome varchar(45) NOT NULL,
		stadio varchar(45) NOT NULL,
        città varchar(45) NOT NULL,
        trofei int CHECK (trofei>=0),
        nomeCampionato varchar(50),
        primary key(nome),
        foreign key(nomeCampionato) references Campionato(nome));
create table Classifica
	(	stagione year NOT NULL,
		punti	int,
        partitegiocate int,
        golFatti int,
        golSubiti int ,
        vittorie int ,
        pareggi int ,
        sconfitte int ,
        nomeSquadra varchar(45) NOT NULL,
        nomeCampionato varchar(50) NOT NULL,
        primary key(stagione,nomeSquadra),
        CONSTRAINT partitegiocatechk CHECK (partitegiocate>=0),
        CONSTRAINT golFattichk CHECK(goLFatti>=0),
        CONSTRAINT golSubitichk CHECK(golSubiti>=0),
        CONSTRAINT vittoriechk CHECK(vittorie>=0),
        CONSTRAINT  pareggichk CHECK(pareggi>=0),
        CONSTRAINT sconfittechk CHECK(sconfitte>=0),
        foreign key(nomeCampionato) references Campionato(nome),
        foreign key(nomeSquadra) references Squadra(nome));
create table Partita
	(	codicePartita varchar(10) NOT NULL,
		giornata int NOT NULL CONSTRAINT giornatachk CHECK(giornata>=1),
        golCasa int CONSTRAINT golCasachk CHECK(golCasa>=0),
        golTrasferta int CONSTRAINT golTrasfertachk CHECK(golTrasferta>=0),
        data date NOT NULL,
        ora time,
        squadraCasa varchar(45) NOT NULL,
        squadraTrasferta varchar(45) NOT NULL,
        nomeCampionato varchar(50),
        primary key(codicePartita),
        foreign key(squadraCasa) references Squadra(nome),
        foreign key(squadraTrasferta) references Squadra(nome),
        foreign key(nomeCampionato) references Campionato(nome)
        );

create table Tesserati
	(	idTesserati varchar(20) NOT NULL,
		nome varchar(20) NOT NULL,
        cognome varchar(20) NOT NULL,
        scadenzaContratto date,
        età int CONSTRAINT etàchk CHECK(età>=16 AND età<=80),
        stipendio int CONSTRAINT stipendiochk CHECK(stipendio>0),
        nomeSquadra varchar(45),
        primary key(idTesserati),
        foreign key(nomeSquadra) references Squadra(nome));
create table Giocatore
	(	ruolo enum ('Portiere','Difensore','Centrocampista','Attaccante') NOT NULL,
		golFatti int CONSTRAINT giocatoregolFattichk CHECK(golFatti>=0),
        golSubiti int CONSTRAINT giocatoregolSubitichk CHECK(golSubiti>=0),
        assist int CONSTRAINT assistchk CHECK(assist>=0),
        partiteGiocate int CONSTRAINT giocatorepartiteGiocatechk CHECK(partiteGiocate>=0),
        trofei int CONSTRAINT giocatoretrofeichk CHECK(trofei>=0),
        cartelliniGialli int CONSTRAINT cartelliniGiallichk CHECK(cartelliniGialli>=0),
        cartelliniRossi int CONSTRAINT cartelliniRossihk CHECK(cartelliniRossi>=0),
        idTesserati varchar(20) NOT NULL,
        primary key(idTesserati),
        foreign key(idTesserati) references Tesserati(idTesserati));
create table StaffTecnico
	(	ruolo varchar (45) NOT NULL,
		idTesserati varchar(20) NOT NULL,
        primary key(idTesserati),
        foreign key(idTesserati) references Tesserati(idTesserati));
create table Dirigenza
	(	ruolo varchar (45) NOT NULL,
		idTesserati varchar(20) NOT NULL,
        primary key(idTesserati),
        foreign key(idTesserati) references Tesserati(idTesserati));
create table StaffMedico
	(	specializzazione varchar (45) NOT NULL,
		idTesserati varchar(20) NOT NULL,
        primary key(idTesserati),
        foreign key(idTesserati) references Tesserati(idTesserati));