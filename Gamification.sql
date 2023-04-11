-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema gamification
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gamification
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gamification` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `gamification` ;

-- -----------------------------------------------------
-- Table `gamification`.`challenges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamification`.`challenges` (
  `idchallenge` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `style` VARCHAR(45) NULL DEFAULT NULL,
  `distance` INT NULL DEFAULT NULL,
  `timp` VARCHAR(15) NULL DEFAULT NULL,
  `tokens` INT NULL DEFAULT NULL,
  `time_spent` VARCHAR(45) NULL DEFAULT '0',
  `idaccept` VARCHAR(500) NULL DEFAULT ',',
  PRIMARY KEY (`idchallenge`),
  INDEX `iduser_idx` (`style` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 52
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into challenges(title,style,distance,timp,tokens) values
('Swim 1k','Freestyle',1000,'0:20:00',10),
('Swim 2k','Freestyle',1000,'0:40:00',10),
('Swim 3k','Freestyle',1000,'1:00:00',20),
('Swim 4k','Freestyle',1000,'1:20:00',25),
('Swim 5k','Freestyle',1000,'1:40:00',25),
('Swim 50 butterfly','Butterfly',50,'0:00:30',20),
('Swim 100 butterfly','Butterfly',50,'0:01:10',25),
('Swim 50 backtroke','Backstroke',50,'0:00:50',5),
('Swim 200 breastroke','Breaststroke',200,'0:03:30',15);
-- -----------------------------------------------------
-- Table `gamification`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gamification`.`users` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `userpassword` VARCHAR(45) NOT NULL,
  `tokens` INT NULL DEFAULT NULL,
  `friends` VARCHAR(1000) NULL DEFAULT ',',
  `points` INT NULL DEFAULT '0',
  `completedC` INT NULL DEFAULT '0',
  `challenges` VARCHAR(500) NULL DEFAULT ',',
  `badges` VARCHAR(500) NULL DEFAULT ',',
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into users(username,userpassword) values
('swimion1','Swimion1'),
('swimion2','Swimion2'),
('swimion3','Swimion3'),
('swimion4','Swimion4'),
('swimion5','Swimion5'),
('swimion6','Swimion6'),
('swimion7','Swimion7'),
('swimion8','Swimion8'),
('swimion9','Swimion9'),
('swimion10','Swimion10'),
('swimion11','Swimion11'),
('swimion12','Swimion12'),
('swimion13','Swimion13');


USE `gamification` ;

-- -----------------------------------------------------
-- procedure AddFriend
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddFriend`(in fr varchar(1000),in userId int)
BEGIN
update users
set friends=fr
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure EditFriends
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `EditFriends`(in fr varchar(1000),in userId int)
BEGIN
update users
set friends=fr
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure completedC
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `completedC`(in sir varchar(500),in userId int)
BEGIN
update users
set acceptedC=sir
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure edit_user
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `edit_user`(in username varchar(45), in password varchar(45),in userId int)
BEGIN
update users
set username=username,userpassword=password
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertC
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertC`(in nr int,in userId int)
BEGIN
update users
set completedC=nr
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertChallengeforUser
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertChallengeforUser`(in ids varchar(500), in userId int)
BEGIN
update users
set challenges=ids
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_badges
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_badges`(in badges varchar(500), in userId int)
BEGIN
update users
set badges=badges
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_points
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_points`(in points int, in userId int)
BEGIN
update users
set points=points
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_tokens
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_tokens`(in tokens int,in userId int)
BEGIN
update users
set tokens=tokens
where idUser=userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_user_in_challenge
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_user_in_challenge`(in userIds varchar(500),in challengeId int)
BEGIN
update challenges
set idaccept=userIds
where idchallenge=challengeId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure new_challenge
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `new_challenge`(in title varchar(45), in style varchar(45), in distance int,in timp varchar(45),in tokens int)
BEGIN
insert into challenges(title,style,distance,timp,tokens)
values(title,style,distance,timp,tokens);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure new_user
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `new_user`(in username varchar(45), in password varchar(45))
BEGIN
insert into users(username,userpassword,tokens,points)
values(username,password,10,0);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ranks
-- -----------------------------------------------------

DELIMITER $$
USE `gamification`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ranks`()
BEGIN
select @rownum:=@rownum+1 ‘rank’, p.* 
from users p, (SELECT @rownum:=0) r 
order by points desc;
END$$

DELIMITER ;

set global event_scheduler= on;
drop event if exists event_activity;
delimiter |

CREATE EVENT event_activity
    ON SCHEDULE
      EVERY 1 day
    DO
      BEGIN
	update group_activities
    set time_spent=time_spent+1;
    
    delete from challenges
    where time_spent>5;
      END |

delimiter ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
