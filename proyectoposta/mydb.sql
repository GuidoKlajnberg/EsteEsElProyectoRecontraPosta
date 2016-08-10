SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Usuario` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `NombreUsuario` TEXT NOT NULL,
  `Contrasena` TEXT NOT NULL,
  `Email` TEXT NOT NULL,
  `Direccion` TEXT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Necesitado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Necesitado` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Necesitado` (
  `idNecesitado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` TEXT NOT NULL,
  `Apellido` TEXT NOT NULL,
  `EdadySexo` TEXT NOT NULL,
  `Direccion` TEXT NOT NULL,
  PRIMARY KEY (`idNecesitado`))
ENGINE = InnoDB
COMMENT = '	';


-- -----------------------------------------------------
-- Table `mydb`.`Categorias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Categorias` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Categorias` (
  `idCategorias` INT NOT NULL AUTO_INCREMENT,
  `Nombre` TEXT NOT NULL,
  PRIMARY KEY (`idCategorias`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CosasNecesitadas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`CosasNecesitadas` ;

CREATE TABLE IF NOT EXISTS `mydb`.`CosasNecesitadas` (
  `idCosaNecesitada` INT NOT NULL AUTO_INCREMENT,
  `Nombre` TEXT NOT NULL,
  `Categorias_idCategorias` INT NOT NULL,
  PRIMARY KEY (`idCosaNecesitada`, `Categorias_idCategorias`),
  UNIQUE INDEX `idCosasNecesitadas_UNIQUE` (`idCosaNecesitada` ASC),
  INDEX `fk_CosasNecesitadas_Categorias1_idx` (`Categorias_idCategorias` ASC),
  CONSTRAINT `fk_CosasNecesitadas_Categorias1`
    FOREIGN KEY (`Categorias_idCategorias`)
    REFERENCES `mydb`.`Categorias` (`idCategorias`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DonacionCosaNecesitada`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`DonacionCosaNecesitada` ;

CREATE TABLE IF NOT EXISTS `mydb`.`DonacionCosaNecesitada` (
  `Usuario_idUsuario` INT NOT NULL,
  `Necesitado_idNecesitado` INT NOT NULL,
  `CosasNecesitadas_idCosaNecesitada` INT NOT NULL,
  INDEX `fk_DonacionCosaNecesitada_Usuario_idx` (`Usuario_idUsuario` ASC),
  INDEX `fk_DonacionCosaNecesitada_Necesitado1_idx` (`Necesitado_idNecesitado` ASC),
  INDEX `fk_DonacionCosaNecesitada_CosasNecesitadas1_idx` (`CosasNecesitadas_idCosaNecesitada` ASC),
  PRIMARY KEY (`Usuario_idUsuario`, `Necesitado_idNecesitado`, `CosasNecesitadas_idCosaNecesitada`),
  CONSTRAINT `fk_DonacionCosaNecesitada_Usuario`
    FOREIGN KEY (`Usuario_idUsuario`)
    REFERENCES `mydb`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DonacionCosaNecesitada_Necesitado1`
    FOREIGN KEY (`Necesitado_idNecesitado`)
    REFERENCES `mydb`.`Necesitado` (`idNecesitado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DonacionCosaNecesitada_CosasNecesitadas1`
    FOREIGN KEY (`CosasNecesitadas_idCosaNecesitada`)
    REFERENCES `mydb`.`CosasNecesitadas` (`idCosaNecesitada`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Habilidades`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Habilidades` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Habilidades` (
  `idHabilidad` INT NOT NULL,
  `Nombre` TEXT NULL,
  PRIMARY KEY (`idHabilidad`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DonacionHabilidades`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`DonacionHabilidades` ;

CREATE TABLE IF NOT EXISTS `mydb`.`DonacionHabilidades` (
  `Habilidades_idHabilidad` INT NOT NULL,
  `Usuario_idUsuario` INT NOT NULL,
  `Necesitado_idNecesitado` INT NOT NULL,
  INDEX `fk_DonacionHabilidades_Habilidades1_idx` (`Habilidades_idHabilidad` ASC),
  INDEX `fk_DonacionHabilidades_Usuario1_idx` (`Usuario_idUsuario` ASC),
  INDEX `fk_DonacionHabilidades_Necesitado1_idx` (`Necesitado_idNecesitado` ASC),
  PRIMARY KEY (`Habilidades_idHabilidad`, `Usuario_idUsuario`, `Necesitado_idNecesitado`),
  CONSTRAINT `fk_DonacionHabilidades_Habilidades1`
    FOREIGN KEY (`Habilidades_idHabilidad`)
    REFERENCES `mydb`.`Habilidades` (`idHabilidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DonacionHabilidades_Usuario1`
    FOREIGN KEY (`Usuario_idUsuario`)
    REFERENCES `mydb`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DonacionHabilidades_Necesitado1`
    FOREIGN KEY (`Necesitado_idNecesitado`)
    REFERENCES `mydb`.`Necesitado` (`idNecesitado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb` ;

-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `mydb`.`view1`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `mydb`.`view1` ;
DROP TABLE IF EXISTS `mydb`.`view1`;
USE `mydb`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `mydb`.`Usuario`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`Usuario` (`idUsuario`, `NombreUsuario`, `Contrasena`, `Email`, `Direccion`) VALUES (0, 'Guido', 'gggkk', 'gklajnberg@hotmail.com', 'Dorrego 2450');
INSERT INTO `mydb`.`Usuario` (`idUsuario`, `NombreUsuario`, `Contrasena`, `Email`, `Direccion`) VALUES (1, 'Uri', 'uuuum', 'urimendelberg@gmail.com', 'Honduras 3750');
INSERT INTO `mydb`.`Usuario` (`idUsuario`, `NombreUsuario`, `Contrasena`, `Email`, `Direccion`) VALUES (2, 'Ezequiel', 'Binker', 'bink@bink.com', 'Mansilla 2233');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`Necesitado`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`Necesitado` (`idNecesitado`, `Nombre`, `Apellido`, `EdadySexo`, `Direccion`) VALUES (0, 'Carlos', 'Gomez', 'Chico', 'Murillo 999');
INSERT INTO `mydb`.`Necesitado` (`idNecesitado`, `Nombre`, `Apellido`, `EdadySexo`, `Direccion`) VALUES (1, 'Rodrigo', 'Perez', 'Hombre', 'Lavalle 1221');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`Categorias`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`Categorias` (`idCategorias`, `Nombre`) VALUES (0, 'Ropa');
INSERT INTO `mydb`.`Categorias` (`idCategorias`, `Nombre`) VALUES (1, 'Cama');
INSERT INTO `mydb`.`Categorias` (`idCategorias`, `Nombre`) VALUES (2, 'Abrigo');
INSERT INTO `mydb`.`Categorias` (`idCategorias`, `Nombre`) VALUES (3, 'Calzado');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`CosasNecesitadas`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`CosasNecesitadas` (`idCosaNecesitada`, `Nombre`, `Categorias_idCategorias`) VALUES (0, 'Remera', 0);
INSERT INTO `mydb`.`CosasNecesitadas` (`idCosaNecesitada`, `Nombre`, `Categorias_idCategorias`) VALUES (1, 'Buzo', 1);
INSERT INTO `mydb`.`CosasNecesitadas` (`idCosaNecesitada`, `Nombre`, `Categorias_idCategorias`) VALUES (2, 'Colchon', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`DonacionCosaNecesitada`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`DonacionCosaNecesitada` (`Usuario_idUsuario`, `Necesitado_idNecesitado`, `CosasNecesitadas_idCosaNecesitada`) VALUES (0, 1, 2);
INSERT INTO `mydb`.`DonacionCosaNecesitada` (`Usuario_idUsuario`, `Necesitado_idNecesitado`, `CosasNecesitadas_idCosaNecesitada`) VALUES (1, 0, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`Habilidades`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`Habilidades` (`idHabilidad`, `Nombre`) VALUES (0, 'Cocinar');
INSERT INTO `mydb`.`Habilidades` (`idHabilidad`, `Nombre`) VALUES (1, 'Manejar auto');
INSERT INTO `mydb`.`Habilidades` (`idHabilidad`, `Nombre`) VALUES (2, 'Manejar moto');
INSERT INTO `mydb`.`Habilidades` (`idHabilidad`, `Nombre`) VALUES (3, 'Atender un telefono');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`DonacionHabilidades`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`DonacionHabilidades` (`Habilidades_idHabilidad`, `Usuario_idUsuario`, `Necesitado_idNecesitado`) VALUES (1, 1, 1);
INSERT INTO `mydb`.`DonacionHabilidades` (`Habilidades_idHabilidad`, `Usuario_idUsuario`, `Necesitado_idNecesitado`) VALUES (2, 0, 0);

COMMIT;

