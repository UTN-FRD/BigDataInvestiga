CREATE DATABASE idb;

CREATE TABLE `ibd`.`files` (
  `id` BIGINT(20) NOT NULL,
  `url` VARCHAR(1000) NULL,
  `fecha_creado` TIMESTAMP NULL,
  `id_investigacion` BIGINT(20) NULL,
  `id_usuario` BIGINT(20) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `ibd`.`files` (
  `id` BIGINT(20) NOT NULL,
  `url` VARCHAR(1000) NULL,
  `fecha_creado` TIMESTAMP NULL,
  `id_investigacion` BIGINT(20) NULL,
  `id_usuario` BIGINT(20) NULL,
  PRIMARY KEY (`id`));
