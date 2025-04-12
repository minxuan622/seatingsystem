create database `seatingArrangements`;
use `seatingArrangements`;

ALTER TABLE `Employee` DROP FOREIGN KEY `employee_ibfk_1`;
drop table if exists `SeatingChart`;
drop table if exists `Employee`;

create table `SeatingChart` (
	`FLOOR_SEAT_SEQ` int primary key,
    `FLOOR_NO` varchar(50),
    `SEAT_NO` varchar(50)
);
    
create table `Employee` (
	`EMP_ID` varchar(5) primary key,
    `NAME` varchar(50),
    `EMAIL` varchar(255),
    `FLOOR_SEAT_SEQ` int,
    foreign key(`FLOOR_SEAT_SEQ`) references `SeatingChart`(`FLOOR_SEAT_SEQ`)
);    

insert into `SeatingChart` values 
	(101, '1樓', '座位1'),
    (102, '1樓', '座位2'),
    (103, '1樓', '座位3'),
    (104, '1樓', '座位4'),
    (201, '2樓', '座位1'),
    (202, '2樓', '座位2'),
    (203, '2樓', '座位3'),
    (204, '2樓', '座位4'),
    (301, '3樓', '座位1'),
    (302, '3樓', '座位2'),
    (303, '3樓', '座位3'),
    (304, '3樓', '座位4'),
    (401, '4樓', '座位1'),
    (402, '4樓', '座位2'),
    (403, '4樓', '座位3'),
    (404, '4樓', '座位4');

insert into `Employee` values 
	('e0001', 'Alice', 'alice@esunbank.com', 101),
    ('e0002', 'Kevin', 'kevin@esunbank.com', 403),
    ('e0003', 'Bob', 'bob@esunbank.com', null),
    ('e0004', 'Max', 'max@esunbank.com', 202);
