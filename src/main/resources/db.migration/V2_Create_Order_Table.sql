DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
    `name` varchar(255) DEFAULT NULL,
    `plateNumber` varchar(255) ,
    `creationTime` date DEFAULT NULL,
    `closeTime` date DEFAULT NULL,
    `orderStatus` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
