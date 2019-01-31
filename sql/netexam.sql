DROP DATABASE IF EXISTS netexam;
CREATE DATABASE netexam;
USE netexam;

CREATE TABLE user (
  id         INT(11)     NOT NULL AUTO_INCREMENT,
  login      VARCHAR(50) NOT NULL,
  `type`     VARCHAR(50) NOT NULL,
  hashkey    VARCHAR(50) NOT NULL,
  firstname  VARCHAR(50) NOT NULL,
  lastname   VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50)          DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user(login)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE user_teacher (
  id         INT(11)     NOT NULL,
  department VARCHAR(50) NOT NULL,
  position   VARCHAR(50) NOT NULL,
  KEY (id),
  UNIQUE KEY user_teacher(id),
  FOREIGN KEY (id) REFERENCES `user` (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE user_student (
  id       INT(11)     NOT NULL,
  semester INT(11)     NOT NULL,
  `group`  VARCHAR(50) NOT NULL,
  UNIQUE KEY user_student(id),
  FOREIGN KEY (id) REFERENCES `user` (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE user_session (
  token   VARCHAR(50) NOT NULL,
  user_id INT(11)     NOT NULL,
  KEY (token),
  UNIQUE KEY user_session(token),
  FOREIGN KEY (user_id) REFERENCES `user` (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE exam (
  id         INT(11)     NOT NULL AUTO_INCREMENT,
  teacher_id INT(11)     NOT NULL,
  name       VARCHAR(50) NOT NULL,
  semester   INT(11)     NOT NULL,
  ready      TINYINT(1)           DEFAULT FALSE,
  `time`     INT(11)              DEFAULT NULL,
  count      INT(11)              DEFAULT NULL,
  details    TINYINT(1)           DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY exam(name, semester),
  FOREIGN KEY (teacher_id) REFERENCES user_teacher (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE question (
  id      INT(11) NOT NULL AUTO_INCREMENT,
  exam_id INT(11) NOT NULL,
  number  INT(11)          DEFAULT NULL,
  content VARCHAR(50)      DEFAULT NULL,
  correct INT(2)           DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (exam_id) REFERENCES exam (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE answer (
  id          INT(11)     NOT NULL AUTO_INCREMENT,
  question_id INT(11)     NOT NULL,
  content     VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (question_id) REFERENCES question (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE student_exam (
  student_id INT(11)  NOT NULL,
  exam_id    INT(11)  NOT NULL,
  enddate    DATETIME NOT NULL,
  result     BLOB     DEFAULT NULL,
  KEY (student_id),
  KEY (exam_id),
  KEY (enddate)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;