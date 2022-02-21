alter table cicero.esperienza_tag drop primary key;
alter table Esperienza_Tag add primary key(Esperienzaid, Tagnome);
alter table Esperienza_Tag add constraint FKEsperienza128327 foreign key (Tagnome) references `Tag` (nome);
