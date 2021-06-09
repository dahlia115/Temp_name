CREATE TABLE member
(
    member_id     INT             NOT NULL PRIMARY KEY,
    email         VARCHAR2(20)    UNIQUE,
    password      VARCHAR2(20)    NOT NULL,
    name          VARCHAR2(20)    NOT NULL, 
    birth         DATE            NULL,
    phone         VARCHAR2(20)    NULL, 
    regdate       DATE            NULL,
    hosting_id    NUMBER          NULL
    );

CREATE SEQUENCE member_id_seq
  START WITH 1
  INCREMENT by 1
  MAXVALUE 10000
  MINVALUE 1
  NOCYCLE;
  