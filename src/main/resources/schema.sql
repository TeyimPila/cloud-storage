CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR(1000),
  password VARCHAR(1000),
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileid INT PRIMARY KEY auto_increment,
    filename VARCHAR(750) NOT NULL UNIQUE,
    contenttype VARCHAR(1000),
    filesize VARCHAR(1000),
    userid INT,
    filedata LONGBLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR (30),
    `key` VARCHAR(1000),
    password VARCHAR(1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS ROLES (
    roleid INT PRIMARY KEY auto_increment,
    role VARCHAR (30)
);
