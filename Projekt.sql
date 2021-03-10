CREATE TABLE wojewodztwo (id_wojewodztwo int, nazwa varchar(20));
CREATE TABLE klub (id_klub int, nazwa varchar(40), id_wojewodztwo int);
CREATE TABLE trener (id_trener int, imie varchar(20), nazwisko varchar(20), id_klub int);
CREATE TABLE zawodnik (id_zawodnik int, imie varchar(20), nazwisko varchar(20), id_klub int, m_k boolean, oplata_startowa boolean, id_druzyna int, id_mikst int);
CREATE TABLE kwalifikacje (id_zawodnik int, mata int, stanowisko char(1), aktual_suma_p int, aktual_suma_x int, aktual_suma_10 int );
CREATE TABLE seria_k (id_seria_k int, id_zawodnik int, punkty varchar(6), suma_p int, suma_x int, suma_10 int );
CREATE TABLE mikst (id_mikst int, nazwa_klub_nr varchar(20));
CREATE TABLE druzyna (id_druzyna int, nazwa_klub_nr varchar(20), m_k boolean);

ALTER TABLE wojewodztwo  add PRIMARY KEY (id_wojewodztwo);
ALTER TABLE klub  add PRIMARY KEY (id_klub);
ALTER TABLE trener  add PRIMARY KEY (id_trener);
ALTER TABLE zawodnik  add PRIMARY KEY (id_zawodnik);
ALTER TABLE seria_k  add PRIMARY KEY (id_seria_k);
ALTER TABLE mikst  add PRIMARY KEY (id_mikst);
ALTER TABLE druzyna  add PRIMARY KEY (id_druzyna);

ALTER TABLE klub  add FOREIGN KEY (id_wojewodztwo) REFERENCES wojewodztwo (id_wojewodztwo);
ALTER TABLE trener  add FOREIGN KEY (id_klub) REFERENCES klub (id_klub);
ALTER TABLE zawodnik add FOREIGN KEY (id_klub) REFERENCES klub (id_klub);
ALTER TABLE zawodnik  add FOREIGN KEY (id_druzyna) REFERENCES druzyna (id_druzyna);
ALTER TABLE zawodnik  add FOREIGN KEY (id_mikst) REFERENCES mikst (id_mikst);
ALTER TABLE kwalifikacje  add FOREIGN KEY (id_zawodnik) REFERENCES zawodnik (id_zawodnik);
ALTER TABLE seria_k  add FOREIGN KEY (id_zawodnik) REFERENCES zawodnik (id_zawodnik);

INSERT INTO wojewodztwo ( id_wojewodztwo, nazwa) VALUES
	(1,'lubelskie'),
	(2,'malopolskie'),
	(3,'mazowieckie'),
	(4,'sląskie');

INSERT INTO klub ( id_klub, nazwa, id_wojewodztwo) VALUES
	(1,'KS Agros Zamosc',1),
	(2,'SL Marymont Warszawa',3),
	(3,'KS Plaszowianka Kraków',2),
	(4,'LKS Lucznik Żywiec',4),
	(5,'MLKS Czarna Strzala Bytom',4);

INSERT INTO trener ( id_trener, imie, nazwisko, id_klub) VALUES
	(1,'Leszek', 'Stempel',1),
	(2,'Adam', 'Pazdyka',2),
	(3,'Ryszard', 'Pacura',3),
	(4,'Jan', 'Lach',4),
	(5,'Dawid', 'Michalski',5);

INSERT INTO zawodnik ( id_zawodnik, imie, nazwisko, id_klub, m_k, oplata_startowa) VALUES
	(1, 'Marcin', 'Kloda',1,TRUE,FALSE), --KS Agros Zamosc
	(2, 'Andrzej', 'Nescior',1,TRUE,FALSE),
	(3, 'Kacper', 'Gucz',1,TRUE,FALSE),
	(4, 'Karol', 'Szeliga',1,TRUE,FALSE),
	(5, 'Kamila', 'Biczak',1,FALSE,FALSE),
	(6, 'Slawomir', 'Naploszek',2,TRUE,FALSE),--SL Marymont Warszawa
	(7, 'Kamila', 'Naploszek',2,FALSE,FALSE),
	(8, 'Zuzanna', 'Bączyk',2,FALSE,FALSE),
	(9, 'Magdalena', 'Zając',2,FALSE,FALSE),
	(10, 'Kacper', 'Sierakowski',2,TRUE,FALSE),
	(11, 'Dariusz', 'Biela',3,TRUE,FALSE),--KS Plaszowianka Kraków
	(12, 'Emilia', 'Walusiak',3,FALSE,FALSE),
	(13, 'Jakub', 'Sluszniak',3,TRUE,FALSE),
	(14, 'Rafal', 'Cichy',3,TRUE,FALSE),
	(15, 'Sylwia', 'Zyzańska',4,FALSE,FALSE),--LKS Lucznik Żywiec
	(16, 'Wioleta', 'Myszor',4,FALSE,FALSE),
	(17, 'Natalia', 'Lesniak',4,FALSE,FALSE),
	(18, 'Oskar', 'Kasprowski',4,TRUE,FALSE),    
	(19, 'Bartosz', 'Kayser',5,TRUE,FALSE),--MLKS Czarna Strzala Bytom
	(20, 'Wojciech', 'Tokarz',5,TRUE,FALSE),
	(21, 'Piotr', 'Andrzejczak',5,TRUE,FALSE),
	(22, 'Martyna', 'Stach', 5, FALSE,FALSE);

INSERT INTO kwalifikacje VALUES
	(1,1,'A',0,0,0),
	(2,2,'A',0,0,0),
	(3,3,'A',0,0,0),
	(4,4,'A',0,0,0),
	(5,1,'A',0,0,0),
	(6,5,'A',0,0,0),
	(7,2,'A',0,0,0),
	(8,3,'A',0,0,0),
	(9,4,'A',0,0,0),
	(10,6,'A',0,0,0),
	(11,1,'B',0,0,0),
	(12,5,'A',0,0,0),
	(13,2,'B',0,0,0),
	(14,3,'B',0,0,0),
	(15,6,'A',0,0,0),
	(16,1,'B',0,0,0),
	(17,2,'B',0,0,0),
	(18,4,'B',0,0,0),
	(19,5,'B',0,0,0),
	(20,6,'B',0,0,0),
	(21,6,'C',0,0,0),
	(22,3,'B',0,0,0);
    
INSERT INTO mikst (id_mikst, nazwa_klub_nr) VALUES
	(1,'Agros'),
	(2,'Marymont 1'),
	(3,'Marymont 2'),
	(4,'Plaszowianka'),
	(5,'Lucznik'),
	(6,'Czarna Strzala');

UPDATE zawodnik set id_mikst = 1 where id_zawodnik=1 or id_zawodnik=5;
UPDATE zawodnik set id_mikst = 2 where id_zawodnik=6 or id_zawodnik=9;
UPDATE zawodnik set id_mikst = 3 where id_zawodnik=10 or id_zawodnik=7;
UPDATE zawodnik set id_mikst = 4 where id_zawodnik=11 or id_zawodnik=12;
UPDATE zawodnik set id_mikst = 5 where id_zawodnik=18 or id_zawodnik=15;
UPDATE zawodnik set id_mikst = 6 where id_zawodnik=20 or id_zawodnik=22;

INSERT INTO druzyna (id_druzyna, nazwa_klub_nr, m_k) VALUES
	(1,'Agros',TRUE	),
	(2,'Marymont',FALSE),
	(3,'Plaszowianka',TRUE),
	(4,'Lucznik',FALSE),
	(5,'Czarna Strzala',TRUE);

UPDATE zawodnik set id_druzyna = 1 where id_zawodnik=1 or id_zawodnik=2 or id_zawodnik=3 or id_zawodnik=4;
UPDATE zawodnik set id_druzyna = 2 where id_zawodnik=7 or id_zawodnik=8 or id_zawodnik=9;
UPDATE zawodnik set id_druzyna = 3 where id_zawodnik=11 or id_zawodnik=13 or id_zawodnik=14;
UPDATE zawodnik set id_druzyna = 4 where id_zawodnik=15 or id_zawodnik=16 or id_zawodnik=17;
UPDATE zawodnik set id_druzyna = 5 where id_zawodnik=19 or id_zawodnik=20 or id_zawodnik=21;

CREATE OR REPLACE FUNCTION dodajKlub( nazwa_ varchar(40), wojewodztwo_ varchar(20)) RETURNS int AS $$
    	DECLARE
    	Rmax RECORD;
    	Ncount RECORD;
    	Rwoj RECORD;
	BEGIN
    	FOR Ncount IN (select count(*) from zawody.klub WHERE nazwa = nazwa_)
    	LOOP
    	END LOOP;
    	IF Ncount.count = 0 THEN
        	FOR Rmax IN (select max(id_klub) from zawody.klub)  
         	LOOP
         	END LOOP;
        	FOR Rwoj IN (select id_wojewodztwo from zawody.wojewodztwo WHERE nazwa = wojewodztwo_)  
         	LOOP
         	END LOOP;
        	INSERT INTO zawody.klub ( id_klub, nazwa, id_wojewodztwo) VALUES (Rmax.max+1, nazwa_, Rwoj.id_wojewodztwo);
        	RETURN 1;
    	ELSE
        	RETURN 0;
    	END IF;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION usunKlub( nazwa_ varchar(40)) RETURNS void AS $$
	BEGIN
    	DELETE FROM zawody.klub WHERE nazwa = nazwa_;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION dodajDrużyne(id1 int, id2 int, id3 int, id4 int, id5 int, nazwa_ varchar(20), m_k boolean) RETURNS int AS $$
	DECLARE
    	Rmax RECORD;
    	Rklub RECORD;
    	Tklub int;
    	tenSamKlub boolean := TRUE;
    	NiktNieMaD boolean := TRUE;
	BEGIN
    	IF id4 = -1 THEN
        	id4 := id1;
    	END IF;
    	IF id5 = -1 THEN
        	id5 := id1;
    	END IF;
    	FOR Rklub IN (select id_klub from zawody.zawodnik WHERE id_zawodnik = id1)  
     	LOOP
     	Tklub := Rklub.id_klub;
     	END LOOP;
    	FOR Rklub IN (select id_klub, id_druzyna from zawody.zawodnik WHERE id_zawodnik = id1 or id_zawodnik = id2 or id_zawodnik = id3 or id_zawodnik = id4 or id_zawodnik = id5)  
     	LOOP
        	IF Rklub.id_klub != Tklub THEN
            	tenSamKlub := FALSE;
        	END IF;
        	IF Rklub.id_druzyna IS NOT NULL THEN
            	NiktNieMaD := FALSE;
        	END IF;
     	END LOOP;
    	IF NiktNieMaD THEN
        	IF tenSamKlub THEN
            	FOR Rmax IN (select max(id_druzyna) from zawody.druzyna)  
             	LOOP
             	END LOOP;
            	INSERT INTO zawody.druzyna (id_druzyna, nazwa_klub_nr, m_k) VALUES (Rmax.max+1,nazwa_, m_k);

            	FOR Rklub IN (select id_zawodnik from zawody.zawodnik WHERE id_zawodnik = id1 or id_zawodnik = id2 or id_zawodnik = id3 or id_zawodnik = id4 or id_zawodnik = id5)  
             	LOOP
                	UPDATE zawody.zawodnik set id_druzyna = Rmax.max+1 where id_zawodnik=Rklub.id_zawodnik;
             	END LOOP;
             	RETURN 1;
        	ELSE
            	RETURN 0;
        	END IF;
    	ELSE
        	RETURN 0;
    	END IF;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION dodajMikst(id_zaw int,nazwa_ varchar(20)) RETURNS int AS $$
	DECLARE
    	Rmax RECORD;
    	Rmikst RECORD;
    	Rklub RECORD;
    	Rzaw RECORD;
    	Tmikst int := 0;
    	tenSamKlub boolean := TRUE;
	BEGIN
    	FOR Rklub IN (select id_mikst from zawody.mikst WHERE nazwa_klub_nr = nazwa_)  
     	LOOP
        	Tmikst := Tmikst +1;
     	END LOOP;
    	IF Tmikst < 2 THEN
        	FOR Rmikst IN (select id_mikst from zawody.mikst WHERE nazwa_klub_nr = nazwa_)  
         	LOOP
         	END LOOP;
        	IF Rmikst.id_mikst IS NOT NULL THEN
                	FOR Rklub IN (select id_klub from zawody.zawodnik WHERE id_mikst = Rmikst.id_mikst)  
                 	LOOP
                 	END LOOP;
                	IF Rklub.id_klub = (select id_klub from zawody.zawodnik where id_zawodnik= id_zaw) THEN
                    	UPDATE zawody.zawodnik set id_mikst = Rmikst.id_mikst where id_zawodnik= id_zaw;
                    	RETURN 1;
                	ELSE
                    	RETURN 0;
                	END IF;
        	ELSE
            	FOR Rmax IN (select max(id_mikst) from zawody.mikst)  
             	LOOP
             	END LOOP;
            	INSERT INTO zawody.mikst (id_mikst, nazwa_klub_nr) VALUES (Rmax.max+1,nazwa_);
            	UPDATE zawody.zawodnik set id_mikst = Rmax.max+1 where id_zawodnik= id_zaw;
            	RETURN 1;
        	END IF;
    	ELSE
        	RETURN 0;
    	END IF;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION usunDrużyne(id_zaw int) RETURNS void AS $$
	DECLARE
    	Rdruzyna RECORD;
    	Rzaw RECORD;
	BEGIN
    	FOR Rdruzyna IN (select id_druzyna from zawody.zawodnik WHERE id_zawodnik = id_zaw)  
     	LOOP
     	END LOOP;
    	FOR Rzaw IN (select id_zawodnik from zawody.zawodnik WHERE id_druzyna = Rdruzyna.id_druzyna)  
     	LOOP
        	UPDATE zawody.zawodnik set id_druzyna = NULL where id_zawodnik=Rzaw.id_zawodnik;
     	END LOOP;
    	DELETE FROM zawody.druzyna WHERE id_druzyna = Rdruzyna.id_druzyna;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION usunMikst(id_zaw int) RETURNS void AS $$
	DECLARE
    	Rmikst RECORD;
    	Rzaw RECORD;
	BEGIN
    	FOR Rmikst IN (select id_mikst from zawody.zawodnik WHERE id_zawodnik = id_zaw)  
     	LOOP
     	END LOOP;
    	FOR Rzaw IN (select id_zawodnik from zawody.zawodnik WHERE id_mikst = Rmikst.id_mikst)  
     	LOOP
        	UPDATE zawody.zawodnik set id_mikst = NULL where id_zawodnik=Rzaw.id_zawodnik;
     	END LOOP;
    	DELETE FROM zawody.mikst WHERE id_mikst = Rmikst.id_mikst;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION dodajZawodnika(imie_ varchar(20), nazwisko_ varchar(20),klub_ varchar(40), m_k boolean) RETURNS void AS $$
	DECLARE
    	Rmax RECORD;
    	Rklub RECORD;
	BEGIN
    	FOR Rmax IN (select max(id_zawodnik) from zawodnik)  
     	LOOP
     	END LOOP;
    	FOR Rklub IN (select id_klub from klub WHERE nazwa = klub_)  
     	LOOP
     	END LOOP;
    	INSERT INTO zawodnik ( id_zawodnik, imie, nazwisko, id_klub, m_k, oplata_startowa) VALUES ( Rmax.max+1, imie_, nazwisko_, Rklub.id_klub, m_k, FALSE);
    	INSERT INTO kwalifikacje (id_zawodnik, aktual_suma_p, aktual_suma_x, aktual_suma_10) VALUES (Rmax.max+1, 0,0,0);
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION usunZawodnika( id int) RETURNS void AS $$
	BEGIN
    	DELETE FROM zawody.kwalifikacje WHERE id_zawodnik = id;
    	DELETE FROM zawody.zawodnik WHERE id_zawodnik = id;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION dodajTrenera(imie_ varchar(20), nazwisko_ varchar(20),klub_ varchar(40)) RETURNS void AS $$
	DECLARE
    	Rmax RECORD;
    	Rklub RECORD;
	BEGIN
    	FOR Rmax IN (select max(id_trener) from zawody.trener)  
     	LOOP
     	END LOOP;
    	FOR Rklub IN (select id_klub from zawody.klub WHERE nazwa = klub_)  
     	LOOP
     	END LOOP;
    	INSERT INTO zawody.trener ( id_trener, imie, nazwisko, id_klub) VALUES ( Rmax.max+1, imie_, nazwisko_, Rklub.id_klub);
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION usunTrenera( id int) RETURNS void AS $$
	BEGIN
    	DELETE FROM zawody.trener WHERE id_trener = id;
	END;
$$ LANGUAGE 'plpgsql';

SELECT id_klub, count(id_zawodnik) FROM zawody.klub JOIN zawody.zawodnik USING(id_klub) WHERE m_k = TRUE GROUP BY id_klub ORDER BY count DESC;

CREATE OR REPLACE FUNCTION generujRozstawienieKwal(m_k_ boolean, ile_mat int) RETURNS int AS $$
	DECLARE
    	Rzaw RECORD;
    	Rklub RECORD;
    	Rile RECORD;
    	next_mata int := 1;
    	ABCD char(1) := 'A';
    	count int := 1;
	BEGIN
    	FOR Rile IN (SELECT count(*) FROM zawody.zawodnik WHERE m_k = m_k_)  
     	LOOP
     	END LOOP;
    	IF Rile.count > ile_mat*4 THEN
        	RETURN 0;
    	ELSE
        	FOR Rklub IN (SELECT id_klub, count(id_zawodnik) FROM zawody.klub JOIN zawody.zawodnik USING(id_klub) WHERE m_k = m_k_ GROUP BY id_klub ORDER BY count DESC)  
         	LOOP
            	FOR Rzaw IN (SELECT id_zawodnik from zawody.zawodnik WHERE id_klub = Rklub.id_klub AND m_k = m_k_)  
            	LOOP
                	UPDATE zawody.kwalifikacje set mata = next_mata, stanowisko = ABCD where id_zawodnik = Rzaw.id_zawodnik;
                	IF next_mata = ile_mat THEN
                    	next_mata := 1;
                    	ABCD := substr('BCD', count, 1);
                    	count := count +1;
                	ELSE
                    	next_mata := next_mata +1;
                	END IF;
            	END LOOP;
         	END LOOP;
        	RETURN 1;
    	END IF;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION metryczka(id_zaw int)
 RETURNS TABLE (Nr_serii varchar(3), _1 char(1), _2 char(1), _3 char(1), sum3 int, sum6 int, sum_max int) AS
$$
DECLARE
    	Spunkty1 int :=0;
    	SpunktyMax int :=0;
    	i int :=1;
    	Rseria RECORD;
    	punkt char(1);
	BEGIN
    	FOR Rseria IN (SELECT * from zawody.seria_k WHERE id_zawodnik = id_zaw ORDER BY id_seria_k)
    	LOOP
        	while i <= 3 LOOP
            	punkt := substring(Rseria.punkty, i, 1);
            	IF punkt = 'x' THEN
                	Spunkty1 := Spunkty1+10;
            	ELSE
                	IF punkt = 'd' THEN
                    	Spunkty1 := Spunkty1+10;
                	ELSE
                    	Spunkty1 := Spunkty1+cast(punkt AS INTEGER);
                	END IF;
            	END IF;
            	i := i +1;
        	END LOOP;

        	Nr_serii := concat(Rseria.id_seria_k - 100*id_zaw,'.');
        	_1 := substring(Rseria.punkty, 1, 1);
        	_2 := substring(Rseria.punkty, 2, 1);
        	_3 := substring(Rseria.punkty, 3, 1);
        	sum3 := Spunkty1;
        	RETURN NEXT;
        	Nr_serii := NULL;
        	_1 := substring(Rseria.punkty, 4, 1);
        	_2 := substring(Rseria.punkty, 5, 1);
        	_3 := substring(Rseria.punkty, 6, 1);
        	sum3 := Rseria.suma_p - Spunkty1;
        	sum6 := Rseria.suma_p;
        	SpunktyMax := SpunktyMax + Rseria.suma_p;
        	sum_max := SpunktyMax;
        	RETURN NEXT;
        	sum6 := NULL;
        	sum_max := NULL;
        	i := 1;
        	Spunkty1 := 0;
    	END LOOP;
	END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION dodajSerie(id_zaw int, punkty varchar(6) ) RETURNS void AS $$
	DECLARE
    	Rmaxserii RECORD;
    	nr_serii int;
    	Spunkty int := 0;
    	Sx int := 0;
    	Sd int := 0;
    	i int :=1;
    	punkt varchar(1);
	BEGIN
    	FOR Rmaxserii IN (SELECT max(id_seria_k) FROM zawody.seria_k WHERE id_zawodnik = id_zaw)  
     	LOOP
     	END LOOP;
    	nr_serii := Rmaxserii.max - 100*id_zaw +1;

    	while i <= 6 LOOP
        	punkt := substring(punkty, i, 1);
        	IF punkt = 'x' THEN
            	Spunkty := Spunkty+10;
            	Sx := Sx +1;
            	Sd := Sd +1;
        	ELSE
            	IF punkt = 'd' THEN
                	Spunkty := Spunkty+10;
                	Sd := Sd +1;
            	ELSE
                	Spunkty := Spunkty+cast(punkt AS INTEGER);
            	END IF;
        	END IF;
        	i := i +1;
    	END LOOP;
    	INSERT INTO zawody.seria_k VALUES (100*id_zaw + nr_serii, id_zaw, punkty, Spunkty, Sx, Sd);
    	UPDATE zawody.kwalifikacje set aktual_suma_p = aktual_suma_p+Spunkty, aktual_suma_x = aktual_suma_x+Sx, aktual_suma_10 = aktual_suma_10+Sd where id_zawodnik=id_zaw;
	END;
$$ LANGUAGE 'plpgsql';

--DROP FUNCTION dodajSerie(int, varchar(6));

select * from dodajSerie(1, 'xxdd99' );
select * from dodajSerie(2, 'xddd98' );
select * from dodajSerie(3, 'dddd96' );
select * from dodajSerie(4, 'dddd97' );
select * from dodajSerie(5, '999854' );
select * from dodajSerie(6, 'xx9993' );
select * from dodajSerie(7, 'dd9991' );
select * from dodajSerie(8, 'xddd92' );
select * from dodajSerie(9, 'xxdd73' );
select * from dodajSerie(10, 'xxdd64' );
select * from dodajSerie(11, 'xxdd63' );
select * from dodajSerie(12, 'xxdd87' );
select * from dodajSerie(13, 'xxdd22' );
select * from dodajSerie(14, 'xxdd31' );
select * from dodajSerie(15, 'xxdd43' );
select * from dodajSerie(16, 'xxdd00' );
select * from dodajSerie(17, 'xxdd91' );
select * from dodajSerie(18, 'xxdd92' );
select * from dodajSerie(19, 'xxdd98' );
select * from dodajSerie(20, 'xxddd9' );
select * from dodajSerie(21, 'xxdd94' );
select * from dodajSerie(22, 'xd9110' );

CREATE OR REPLACE FUNCTION poprawSerie(id_zaw int, nr_serii int, punkty_v varchar(6) ) RETURNS void AS $$
	DECLARE
    	Spunkty int := 0;
    	Sx int := 0;
    	Sd int := 0;
    	Stara_Seria RECORD;
    	i int :=1;
    	punkt varchar(1);
	BEGIN
    	while i <= 6 LOOP
        	punkt := substring(punkty_v, i, 1);
        	IF punkt = 'x' THEN
            	Spunkty := Spunkty+10;
            	Sx := Sx +1;
            	Sd := Sd +1;
        	ELSE
            	IF punkt = 'd' THEN
                	Spunkty := Spunkty+10;
                	Sd := Sd +1;
            	ELSE
                	Spunkty := Spunkty+cast(punkt AS INTEGER);
            	END IF;
        	END IF;
        	i := i +1;
    	END LOOP;
    	FOR Stara_Seria IN (SELECT * FROM zawody.seria_k WHERE id_seria_k = 100*id_zaw + nr_serii)  
     	LOOP
     	END LOOP;
    	UPDATE zawody.seria_k set punkty = punkty_v, suma_p = Spunkty, suma_x = Sx, suma_10 = Sd where id_seria_k = 100*id_zaw + nr_serii;
    	UPDATE zawody.kwalifikacje set aktual_suma_p = aktual_suma_p-Stara_Seria.suma_p+Spunkty, aktual_suma_x = aktual_suma_x-Stara_Seria.suma_x+Sx, aktual_suma_10 = aktual_suma_10-Stara_Seria.suma_10+Sd where id_zawodnik=id_zaw;
	END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE VIEW rozstawienie_kobiet AS
SELECT mata, stanowisko, nazwisko, imie, nazwa as klub, id_zawodnik FROM kwalifikacje JOIN zawodnik USING(id_zawodnik) JOIN klub USING(id_klub) WHERE m_k = FALSE ORDER BY mata, stanowisko;

CREATE OR REPLACE VIEW rozstawienie_mezczyzn AS
SELECT mata, stanowisko, nazwisko, imie, nazwa as klub, id_zawodnik FROM kwalifikacje JOIN zawodnik USING(id_zawodnik) JOIN klub USING(id_klub) WHERE m_k = TRUE ORDER BY mata, stanowisko;

CREATE OR REPLACE VIEW lista_mezczyzn AS
SELECT nazwisko, imie, klub.nazwa, id_zawodnik,
(SELECT nazwa_klub_nr from zawodnik join druzyna using(id_druzyna) where id_zawodnik = z.id_zawodnik) as druzyna,
(SELECT nazwa_klub_nr from zawodnik join mikst using(id_mikst) where id_zawodnik = z.id_zawodnik) as mikst
FROM zawodnik z JOIN klub using(id_klub) WHERE z.m_k = TRUE ORDER BY nazwisko;

CREATE OR REPLACE VIEW lista_kobiet AS
SELECT nazwisko, imie, klub.nazwa, id_zawodnik,
(SELECT nazwa_klub_nr from zawodnik join druzyna using(id_druzyna) where id_zawodnik = z.id_zawodnik) as druzyna,
(SELECT nazwa_klub_nr from zawodnik join mikst using(id_mikst) where id_zawodnik = z.id_zawodnik) as mikst
FROM zawodnik z JOIN klub using(id_klub) WHERE z.m_k = FALSE ORDER BY nazwisko;

CREATE OR REPLACE FUNCTION aktualne_wyniki_kwal(m_k_ boolean)
 RETURNS TABLE (pozycja int, nazwisko_imie varchar(40), klub varchar(40), suma int, x_10 varchar(8)) AS
$$
DECLARE
	var_r RECORD;
	temp_r RECORD;
	usun_x_10 boolean := TRUE;
BEGIN
 FOR var_r IN (SELECT RANK() OVER( ORDER BY aktual_suma_p DESC, aktual_suma_x DESC, aktual_suma_10 DESC) as miejsce,
            	nazwisko, imie, nazwa as klub, aktual_suma_p as suma_p, aktual_suma_x as x, aktual_suma_10 as d
            	FROM zawody.kwalifikacje kw JOIN zawody.zawodnik USING(id_zawodnik) JOIN zawody.klub USING(id_klub) WHERE m_k = m_k_)  
 LOOP
     	pozycja := var_r.miejsce ;
    	nazwisko_imie := concat(var_r.nazwisko,' ',var_r.imie);
    	klub  := var_r.klub ;
    	suma  := var_r.suma_p ;

    	FOR temp_r IN (SELECT nazwisko as temp_n, aktual_suma_p as temp_s FROM zawody.kwalifikacje kw JOIN zawody.zawodnik USING(id_zawodnik)
                    	WHERE m_k = m_k_ and aktual_suma_p = var_r.suma_p)  
     	LOOP
         	IF var_r.nazwisko != temp_r.temp_n THEN
             	usun_x_10 := FALSE;
         	END IF;
       	END LOOP;
       	IF usun_x_10 THEN
           	x_10 := NULL;
       	ELSE
           	x_10 :=concat(var_r.x,'/',var_r.d);
       	END IF;
       	usun_x_10 := TRUE;
       	RETURN NEXT;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';

select * from usun_x_10_kwal_kobiet();
DROP FUNCTION usun_x_10_kwal_kobiet();

CREATE OR REPLACE VIEW kwal_kobiet AS
select * from aktualne_wyniki_kwal(FALSE);

CREATE OR REPLACE VIEW kwal_mezczyzn AS
select * from aktualne_wyniki_kwal(TRUE);

UPDATE kwalifikacje set aktual_suma_p = aktual_suma_p+100 where id_zawodnik = 22;
select * from kwal_kobiet;
UPDATE kwalifikacje set aktual_suma_p = aktual_suma_p-100 where id_zawodnik = 22;





CREATE OR REPLACE FUNCTION suma_p_dru(id_dru int) RETURNS int AS
$$
DECLARE
	var_r RECORD;
	p_pierwszy int :=0;
	p_drugi int :=0;
	p_trzeci int :=0;
BEGIN
 	FOR var_r IN (SELECT aktual_suma_p FROM zawodnik JOIN kwalifikacje USING(id_zawodnik) WHERE id_druzyna = id_dru)  
 	LOOP
     	IF var_r.aktual_suma_p > p_pierwszy THEN
         	p_trzeci := p_drugi;
         	p_drugi := p_pierwszy;
         	p_pierwszy := var_r.aktual_suma_p;
     	ELSE
         	IF var_r.aktual_suma_p > p_drugi THEN
             	p_trzeci := p_drugi;
             	p_drugi := var_r.aktual_suma_p;
         	ELSE
             	IF var_r.aktual_suma_p > p_trzeci THEN
                 	p_trzeci = var_r.aktual_suma_p;
             	END IF;
         	END IF;
     	END IF;
 	END LOOP;
 	RETURN p_pierwszy+p_drugi+p_trzeci;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION suma_x_dru(id_dru int) RETURNS int AS
$$
DECLARE
	var_r RECORD;
	x_pierwszy int :=0;
	x_drugi int :=0;
	x_trzeci int :=0;
BEGIN
 	FOR var_r IN (SELECT aktual_suma_x FROM zawodnik JOIN kwalifikacje USING(id_zawodnik) WHERE id_druzyna = id_dru)  
 	LOOP
     	IF var_r.aktual_suma_x > x_pierwszy THEN
         	x_trzeci := x_drugi;
         	x_drugi := x_pierwszy;
         	x_pierwszy := var_r.aktual_suma_x;
     	ELSE
         	IF var_r.aktual_suma_x > x_drugi THEN
             	x_trzeci := x_drugi;
             	x_drugi := var_r.aktual_suma_x;
         	ELSE
             	IF var_r.aktual_suma_x > x_trzeci THEN
                 	x_trzeci = var_r.aktual_suma_x;
             	END IF;
         	END IF;
     	END IF;
 	END LOOP;
 	RETURN x_pierwszy+x_drugi+x_trzeci;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION suma_10_dru(id_dru int) RETURNS int AS
$$
DECLARE
	var_r RECORD;
	d_pierwszy int :=0;
	d_drugi int :=0;
	d_trzeci int :=0;
BEGIN
 	FOR var_r IN (SELECT aktual_suma_10 FROM zawodnik JOIN kwalifikacje USING(id_zawodnik) WHERE id_druzyna = id_dru)  
 	LOOP
     	IF var_r.aktual_suma_10 > d_pierwszy THEN
         	d_trzeci := d_drugi;
         	d_drugi := d_pierwszy;
         	d_pierwszy := var_r.aktual_suma_10;
     	ELSE
         	IF var_r.aktual_suma_10 > d_drugi THEN
             	d_trzeci := d_drugi;
             	d_drugi := var_r.aktual_suma_10;
         	ELSE
             	IF var_r.aktual_suma_10 > d_trzeci THEN
                 	d_trzeci = var_r.aktual_suma_10;
             	END IF;
         	END IF;
     	END IF;
 	END LOOP;
 	RETURN d_pierwszy+d_drugi+d_trzeci;
END;
$$  LANGUAGE 'plpgsql';

SELECT RANK() OVER( ORDER BY suma_p_dru(id_druzyna) DESC, suma_x_dru(id_druzyna) DESC, suma_10_dru(id_druzyna) DESC) as miejsce,
nazwa_klub_nr, suma_p_dru(id_druzyna), concat(suma_x_dru(id_druzyna),'/',suma_10_dru(id_druzyna)) as x_10_ FROM druzyna WHERE m_k = TRUE;

SELECT RANK() OVER( ORDER BY suma_p_dru(id_druzyna) DESC, suma_x_dru(id_druzyna) DESC, suma_10_dru(id_druzyna) DESC) as miejsce,
nazwa_klub_nr, suma_p_dru(id_druzyna), concat(suma_x_dru(id_druzyna),'/',suma_10_dru(id_druzyna)) as x_10_ FROM druzyna WHERE m_k = FALSE;


CREATE OR REPLACE FUNCTION aktual_wyniki_kwal_druzyn(m_k_ boolean)
 RETURNS TABLE (pozycja int, drużyna varchar(40), suma int, x_10 varchar(8)) AS
$$
DECLARE
	var_r RECORD;
	temp_r RECORD;
	usun_x_10 boolean := TRUE;
BEGIN
 FOR var_r IN (SELECT RANK() OVER( ORDER BY suma_p_dru(id_druzyna) DESC, suma_x_dru(id_druzyna) DESC, suma_10_dru(id_druzyna) DESC) as miejsce,
            	nazwa_klub_nr, suma_p_dru(id_druzyna), concat(suma_x_dru(id_druzyna),'/',suma_10_dru(id_druzyna)) as x_10_ FROM zawody.druzyna WHERE m_k = m_k_)  
 LOOP
     	pozycja := var_r.miejsce;
    	drużyna := var_r.nazwa_klub_nr;
    	suma  := var_r.suma_p_dru;

    	FOR temp_r IN (SELECT RANK() OVER( ORDER BY suma_p_dru(id_druzyna) DESC, suma_x_dru(id_druzyna) DESC, suma_10_dru(id_druzyna) DESC) as miejsce,
            	nazwa_klub_nr, id_druzyna, suma_p_dru(id_druzyna), concat(suma_x_dru(id_druzyna),'/',suma_10_dru(id_druzyna)) as x_10_ FROM zawody.druzyna WHERE m_k = m_k_ and suma_p_dru(id_druzyna) = var_r.suma_p_dru)  
     	LOOP
         	IF var_r.nazwa_klub_nr != temp_r.nazwa_klub_nr THEN
             	usun_x_10 := FALSE;
         	END IF;   
       	END LOOP;

       	IF usun_x_10 THEN
           	x_10 := NULL;
       	ELSE
           	x_10 :=var_r.x_10_;
       	END IF;
       	usun_x_10 := TRUE;
       	RETURN NEXT;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';

select * from aktual_wyniki_kwal_druzyn(TRUE);
UPDATE kwalifikacje set aktual_suma_p = aktual_suma_p+5 where id_zawodnik = 21;
select * from aktual_wyniki_kwal_druzyn(TRUE);
UPDATE kwalifikacje set aktual_suma_p = aktual_suma_p-5 where id_zawodnik = 21;
select * from aktual_wyniki_kwal_druzyn(TRUE);



CREATE OR REPLACE FUNCTION suma_p_mikst(id_mik int) RETURNS int AS
$$
DECLARE
	var_r RECORD;
	p_pierwszy int :=-1;
BEGIN
 	FOR var_r IN (SELECT aktual_suma_p FROM zawodnik JOIN kwalifikacje USING(id_zawodnik) WHERE id_mikst = id_mik)  
 	LOOP
     	IF p_pierwszy = -1 THEN
         	p_pierwszy := var_r.aktual_suma_p;
     	END IF;
 	END LOOP;
 	RETURN p_pierwszy+var_r.aktual_suma_p;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION suma_x_mikst(id_mik int) RETURNS int AS
$$
DECLARE
	var_r RECORD;
	x_pierwszy int :=-1;
BEGIN
 	FOR var_r IN (SELECT aktual_suma_x FROM zawodnik JOIN kwalifikacje USING(id_zawodnik) WHERE id_mikst = id_mik)  
 	LOOP
     	IF x_pierwszy = -1 THEN
         	x_pierwszy := var_r.aktual_suma_x;
     	END IF;
 	END LOOP;
 	RETURN x_pierwszy+var_r.aktual_suma_x;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION suma_10_mikst(id_mik int) RETURNS int AS
$$
DECLARE
	var_r RECORD;
	d_pierwszy int :=-1;
BEGIN
 	FOR var_r IN (SELECT aktual_suma_10 FROM zawodnik JOIN kwalifikacje USING(id_zawodnik) WHERE id_mikst = id_mik)  
 	LOOP
     	IF d_pierwszy = -1 THEN
         	d_pierwszy := var_r.aktual_suma_10;
     	END IF;
 	END LOOP;
 	RETURN d_pierwszy+var_r.aktual_suma_10;
END;
$$  LANGUAGE 'plpgsql';

SELECT RANK() OVER( ORDER BY suma_p_mikst(id_mikst) DESC, suma_x_mikst(id_mikst) DESC, suma_10_mikst(id_mikst) DESC) as miejsce,
nazwa_klub_nr, suma_p_mikst(id_mikst), concat(suma_x_mikst(id_mikst),'/',suma_10_mikst(id_mikst)) as x_10_ FROM mikst;

CREATE OR REPLACE FUNCTION aktual_wyniki_kwal_mikst()
 RETURNS TABLE (pozycja int, mikst varchar(40), suma int, x_10 varchar(8)) AS
$$
DECLARE
	var_r RECORD;
	temp_r RECORD;
	usun_x_10 boolean := TRUE;
BEGIN
 FOR var_r IN (SELECT RANK() OVER( ORDER BY suma_p_mikst(id_mikst) DESC, suma_x_mikst(id_mikst) DESC, suma_10_mikst(id_mikst) DESC) as miejsce,
            	nazwa_klub_nr, suma_p_mikst(id_mikst), concat(suma_x_mikst(id_mikst),'/',suma_10_mikst(id_mikst)) as x_10_ FROM mikst)  
 LOOP
     	pozycja := var_r.miejsce;
    	mikst := var_r.nazwa_klub_nr;
    	suma  := var_r.suma_p_mikst;

    	FOR temp_r IN (SELECT RANK() OVER( ORDER BY suma_p_mikst(id_mikst) DESC, suma_x_mikst(id_mikst) DESC, suma_10_mikst(id_mikst) DESC) as miejsce,
            	nazwa_klub_nr, suma_p_mikst(id_mikst), concat(suma_x_mikst(id_mikst),'/',suma_10_mikst(id_mikst)) as x_10_ FROM mikst where suma_p_mikst(id_mikst) = var_r.suma_p_mikst)  
     	LOOP
         	IF var_r.nazwa_klub_nr != temp_r.nazwa_klub_nr THEN
             	usun_x_10 := FALSE;
         	END IF;   
       	END LOOP;

       	IF usun_x_10 THEN
           	x_10 := NULL;
       	ELSE
           	x_10 :=var_r.x_10_;
       	END IF;
       	usun_x_10 := TRUE;
       	RETURN NEXT;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';

select * from aktual_wyniki_kwal_mikst();
UPDATE kwalifikacje set aktual_suma_p = aktual_suma_p+18 where id_zawodnik = 22;
select * from aktual_wyniki_kwal_mikst();
UPDATE kwalifikacje set aktual_suma_p = aktual_suma_p-18 where id_zawodnik = 22;
select * from aktual_wyniki_kwal_mikst();

CREATE OR REPLACE FUNCTION oblicz_kwal_klubow()
 RETURNS TABLE (klub varchar(40), suma int) AS
$$
DECLARE
	var_r RECORD;
	temp_poz RECORD;
	sumaP int :=0;
BEGIN
 FOR var_r IN (SELECT nazwa FROM klub)  
 LOOP
    	klub := var_r.nazwa;
    	FOR temp_poz IN (SELECT pozycja FROM kwal_kobiet WHERE kwal_kobiet.klub = var_r.nazwa )
    	LOOP
        	sumaP := sumaP + 21 - temp_poz.pozycja;  
    	END LOOP;
    	FOR temp_poz IN (SELECT pozycja FROM kwal_mezczyzn WHERE kwal_mezczyzn.klub = var_r.nazwa )
    	LOOP
        	sumaP := sumaP + 21 - temp_poz.pozycja;  
    	END LOOP;
    	suma := sumaP;
    	RETURN NEXT;
    	sumaP :=0;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION aktual_wyniki_kwal_klubow()
 RETURNS TABLE (pozycja int, klub varchar(40), suma int) AS
$$
DECLARE
	var_r RECORD;
	poz int :=1;
BEGIN
 FOR var_r IN (SELECT * FROM oblicz_kwal_klubow() ORDER BY suma DESC)  
 LOOP
    	pozycja := poz;
    	klub := var_r.klub;
    	suma := var_r.suma;
    	RETURN NEXT;
    	poz := poz +1;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION oblicz_kwal_wojewodztw()
 RETURNS TABLE (wojewodztwo varchar(40), suma int) AS
$$
DECLARE
	var_r RECORD;
	temp_poz RECORD;
	sumaP int :=0;
BEGIN
 FOR var_r IN (SELECT nazwa FROM wojewodztwo)  
 LOOP
    	wojewodztwo := var_r.nazwa;
    	FOR temp_poz IN (SELECT ak.suma FROM aktual_wyniki_kwal_klubow() as ak JOIN zawody.klub ON klub = nazwa JOIN wojewodztwo USING(id_wojewodztwo) WHERE wojewodztwo.nazwa = var_r.nazwa )
    	LOOP
        	sumaP := sumaP + temp_poz.suma;  
    	END LOOP;
    	suma := sumaP;
    	RETURN NEXT;
    	sumaP :=0;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION aktual_wyniki_kwal_wojewodztw()
 RETURNS TABLE (pozycja int, wojewodztwo varchar(40), suma int) AS
$$
DECLARE
	var_r RECORD;
	poz int :=1;
BEGIN
 FOR var_r IN (SELECT * FROM oblicz_kwal_wojewodztw() ORDER BY suma DESC)  
 LOOP
    	pozycja := poz;
    	wojewodztwo := var_r.wojewodztwo;
    	suma := var_r.suma;
    	RETURN NEXT;
    	poz := poz +1;
 END LOOP;
END;
$$  LANGUAGE 'plpgsql';
