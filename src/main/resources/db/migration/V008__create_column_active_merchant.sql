ALTER TABLE merchant ADD active TINYINT(1) NOT NULL;
UPDATE merchant SET active = true;