CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20) UNIQUE,
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

--INSERT INTO USERS(username, salt, password, firstname, lastname)
--VALUES('trungvq8',
--'AAAAAAAAAAAAAAAAAAAAAA==',
--'B50dio7CQHgpxwwHj723T3IEWQQVlol29e+g1kSqTc+G8vUVtTWlvo1Tc39PpYGnnobknoj0L5am/DL2D8ZDCRwxFwVxYpzZznqEqIxV6AWPqz1p+tfUPHxjMAVV7Uyl0wdat0kU5L13Klo4UEyVJ7JEKOqvscL1sbnELT6qUxLVXRSqj5YaHFvnV5Y0GWM+UCECd+Tbje7m4XQfLQJBmXnJrOc7OppBixxQOGKLYbqADSWt87JkdDef0oXAS1LcnP8vVpngr2LnvtoYWXoRPZ8QdI9KNagdhQ6wqYJ4X5X3SHx/mpUJ5PPTJuVMEr+E+TmI/ZwjaxFcqAP/quRlog==',
--'Vu',
--'Trung');

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);
