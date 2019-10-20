DROP TABLE IF EXISTS `parkingLot`;

CREATE TABLE `parkingLot` (
    `name` varchar(255) DEFAULT NULL,
    `capacity` int(100),
    `location` varchar(255) DEFAULT NULL
    PRIMARY KEY (`id`)
);