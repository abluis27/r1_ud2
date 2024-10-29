DROP TABLE IF EXISTS alumnosGestorFinal;
CREATE TABLE alumnosGestorFinal(
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    nota FLOAT,
    nivel ENUM("FPB", "CFGM", "CFGS")
);