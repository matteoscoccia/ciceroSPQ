INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('caimarche@gmail.com','CAI MARCHE',NULL,'76jo98lk','a',NULL);
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('dante.alighieri@gmail.com','Dante','Alighieri','da77yh','c','caimarche@gmail.com');
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('fai@gmail.com','FAI',NULL,'1234','a',NULL);
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('francesca.gialli@gmail.com','Francesca','Gialli','pw427','c',NULL);
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('lucia.verdi@gmail.com','Lucia','Verdi','pass123','t',NULL);
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('luisa.spagnoli@gmail.com','Luisa','Spagnoli','pw4guu','c','fai@gmail.com');
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('maria.bianchi@gmail.com','Maria','Bianchi','rs5567','c','fai@gmail.com');
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('mario.rossi@gmail.com','Mario','Rossi','password','c','fai@gmail.com');
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('matteo.tarsi@gmail.com','Matteo','Tarsi','1mm28g','t',NULL);
INSERT INTO `utente` (`email`,`nome`,`cognome`,`password`,`tipo`,`emailAssociazione`) VALUES ('turismoLombardia@gmail.com','Turismo Lombardia',NULL,'TL78x*','a',NULL);

INSERT INTO toponimo VALUES 
("Italia","Italia"),
("Marche", "Italia"),
("Lombardia", "Italia"),
("Abruzzo", "Italia"),
("Trentino Alto Adige", "Italia"),
("Lazio", "Italia"),
("Campania", "Italia"),
("Sicilia", "Italia"),
("Umbria", "Italia"),
("Sardegna", "Italia"),
("Appennino Centrale", "Italia"),
("Fermo","Marche"),
("Ancona","Marche"),
("Ascoli Piceno","Marche"),
("Riviera Marchigiana","Marche"),
("Colline Marchigiane","Marche"),
("Milano","Lombardia"),
("Bergamo", "Lombardia"),
("Industria Lombarda", "Lombardia"),
("Napoli", "Campania"),
("Costiera Amalfitana", "Campania"),
("Cilento", "Campania"),
("Roma","Lazio"),
("Alpi", "Italia"),
("Catania", "Sicilia"),
("Palermo", "Sicilia"),
("Valle dei Templi", "Sicilia"),
("Isole Eolie", "Sicilia"),
("Pescara","Abruzzo"),
("Chieti", "Abruzzo"),
("Costa Abruzzese", "Abruzzo"),
("Appennino Abruzzeze", "Abruzzo"),
("Costa Adriatica", "Italia");

INSERT INTO `esperienza` (`id`,`titolo`,`descrizione`,`data`,`postiDisponibili`,`postiMassimi`,`postiMinimi`,`prezzo`,`emailGuida`,`toponimo`) VALUES (1,'Passeggiata sulla riviera marchigiana','Una piacevole passeggiata nelle principali località balneari della costa marchigiana.','2022-04-16',13,15,2,20,NULL,'Riviera Marchigiana');
INSERT INTO `esperienza` (`id`,`titolo`,`descrizione`,`data`,`postiDisponibili`,`postiMassimi`,`postiMinimi`,`prezzo`,`emailGuida`,`toponimo`) VALUES (2,'Degustazione di vini nelle principali cantine abruzzesi','Percorso enogastronomico in più tappe nelle principali cantine abruzzesi.','2022-03-03',30,30,1,25,'dante.alighieri@gmail.com','Abruzzo');
INSERT INTO `esperienza` (`id`,`titolo`,`descrizione`,`data`,`postiDisponibili`,`postiMassimi`,`postiMinimi`,`prezzo`,`emailGuida`,`toponimo`) VALUES (3,'Escursione sulle principali montagne dell\'Appennino centrale','Escursione adatta ad alpinisti esperti, dove toccheremo le varie vette dell\'appennino centrale.','2022-03-16',14,10,5,20,'mario.rossi@gmail.com','Appennino Centrale');
INSERT INTO `esperienza` (`id`,`titolo`,`descrizione`,`data`,`postiDisponibili`,`postiMassimi`,`postiMinimi`,`prezzo`,`emailGuida`,`toponimo`) VALUES (4,'Aperitivo con tramonto sulla costiera amalfitana','Rilassati degustando un ottimo aperitivo e godendo del suggestiovo paesaggio della costiera Amalfitana.','2022-06-22',20,20,5,35,'mario.rossi@gmail.com','Costiera Amalfitana');
INSERT INTO `esperienza` (`id`,`titolo`,`descrizione`,`data`,`postiDisponibili`,`postiMassimi`,`postiMinimi`,`prezzo`,`emailGuida`,`toponimo`) VALUES (51349,'Passeggiata nella Valle dei Templi','Passeggiata tra le rovine delle antiche civiltÃ ','2022-05-05',8,10,2,32,'mario.rossi@gmail.com','Valle dei Templi');

INSERT INTO `tag` (`nome`) VALUES ('Aperitivo');
INSERT INTO `tag` (`nome`) VALUES ('Campagna');
INSERT INTO `tag` (`nome`) VALUES ('Collina');
INSERT INTO `tag` (`nome`) VALUES ('Cultura');
INSERT INTO `tag` (`nome`) VALUES ('Manifattura');
INSERT INTO `tag` (`nome`) VALUES ('Mare');
INSERT INTO `tag` (`nome`) VALUES ('Montagna');
INSERT INTO `tag` (`nome`) VALUES ('Musei');
INSERT INTO `tag` (`nome`) VALUES ('Natura');
INSERT INTO `tag` (`nome`) VALUES ('Panorama');
INSERT INTO `tag` (`nome`) VALUES ('Tramonto');
INSERT INTO `tag` (`nome`) VALUES ('Vino');

INSERT INTO `tagdaapprovare` (`nome`) VALUES ('Industria');

INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (4,'Aperitivo');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (2,'Campagna');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (1,'Collina');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (2,'Collina');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (3,'Collina');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (51349,'Cultura');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (1,'Mare');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (4,'Mare');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (3,'Montagna');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (51349,'Musei');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (1,'Natura');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (3,'Natura');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (4,'Tramonto');
INSERT INTO `esperienza_tag` (`Esperienzaid`,`Tag`) VALUES (2,'Vino');

INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('dante.alighieri@gmail.com','2022-04-05');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('dante.alighieri@gmail.com','2022-04-06');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('luisa.spagnoli@gmail.com','2022-04-17');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('maria.bianchi@gmail.com','2022-03-05');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('maria.bianchi@gmail.com','2022-03-06');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('maria.bianchi@gmail.com','2022-03-30');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('maria.bianchi@gmail.com','2022-05-28');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-03-10');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-03-11');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-04-11');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-04-17');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-04-18');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-04-19');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-04-20');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-05-05');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-05-22');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-06-11');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-06-13');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-06-23');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-07-12');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-08-18');
INSERT INTO `disponibilita` (`emailCicerone`,`data`) VALUES ('mario.rossi@gmail.com','2022-08-19');

INSERT INTO `pagamento` (`id`,`importo`,`Utenteemail`,`Esperienzaid`) VALUES (1,60,'matteo.tarsi@gmail.com',1);
INSERT INTO `pagamento` (`id`,`importo`,`Utenteemail`,`Esperienzaid`) VALUES (2,100,'matteo.tarsi@gmail.com',3);
INSERT INTO `pagamento` (`id`,`importo`,`Utenteemail`,`Esperienzaid`) VALUES (3,210,'matteo.tarsi@gmail.com',4);
INSERT INTO `pagamento` (`id`,`importo`,`Utenteemail`,`Esperienzaid`) VALUES (382017,64,'lucia.verdi@gmail.com',51349);

INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (1,'mario.bianchi@libero.it',1);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (2,'luisa.giallini@gmail.com',1);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (3,'giuseppe.verdi@tiscali.it',3);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (4,'francesca.testa@gmail.com',3);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (5,'iginio.massari@gmail.com',3);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (6,'bruno.barbieri@gmail.com',3);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (7,'antonino.cannavacciolo@gmail.com',4);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (8,'giorgio.locatelli@gmail.com',4);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (9,'antonia.klugmann@gmail.com',4);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (10,'simone.scipioni@gmail.com',4);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (11,'terry.giacomelli@gmail.com',4);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (12,'matteo.tarsi@gmail.com',4);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (13,'matteo.tarsi@gmai.com',3);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (14,'matteo.tarsi@gmail.com',1);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (74697,'sergio.mattarella@gmail.com',51349);
INSERT INTO `partecipante` (`id`,`email`,`Esperienzaid`) VALUES (349886,'mattia2020@gmail.com',51349);

INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Sirolo','tappa spiaggia Sirolo','Via Leopardi 13',1);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Civitanova Marche','Spiaggia civitanovese','Lungomare Antonio Gramsci',1);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('San Benedetto Del Tronto','Lungomare della riviera delle palme','Lungomare Dante Alighieri',1);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Cantina Masciarelli','Degustazione dei migliori vini della cantina','Via Leopoldo 15',2);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Cantina Frentana','Assaggio delle nuove proposte','Via Medaglie D\'oro 22',2);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Monte Vettore','Principale vetta marchigiana','Nessun indirizzo',3);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Monte Sibilla','Tappa nella montagna più suggestiva dell\'Appennino','Nessun indirizzo',3);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Beerstrot','Birra sulla spiaggia con degustazione di snack','Via Amalfi 33',4);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Vino e co.','Degustazione di vini accompagnata da pesce locale','Via Amalfi 15',4);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Pizzeria da Michele','Assaggio della vera pizza napoletana','Via Amalfi 10',4);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Agrigento','Prima tappa. Ristoro nella parte moderna della cittÃ .','Agrigento AG',51349);
INSERT INTO `tappa` (`nome`,`descrizione`,`indirizzo`,`Esperienzaid`) VALUES ('Valle dei Templi','Passeggiata tra le meraviglie del passato delle civiltÃ  di queste terre.','Via dei Templi 22',51349);
