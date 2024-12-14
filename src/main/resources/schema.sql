CREATE TABLE diary_page (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    WrittenDate DATE,
    Entry varchar(255)
);

CREATE TABLE lesson (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Entry varchar(255)
);

CREATE TABLE question_solution (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Question varchar(255),
    Options varchar(255),
    OptionSolution varchar(255),
    Description varchar(255),
    lesson_id BIGINT,
    FOREIGN  KEY(lesson_id) REFERENCES lesson(ID)
);

CREATE TABLE users (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    FirstName varchar(255),
    LastName varchar(255),
    Email varchar(255),
    Password varchar(255),
);