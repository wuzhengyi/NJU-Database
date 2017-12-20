CREATE DATABASE lab2;

USE lab2;

/*
职工 staff
姓名 sname
工号 sno
年龄 age
年薪 salary
部门编号 dno
*/

CREATE TABLE Staff
(
    SNAME char(8) NOT NULL,
    SNO int NOT NULL,
    AGE int DEFAULT 0, -- 默认值约束
    SALARY int,
    DNO int, -- FOREIGN KEY REFERENCES Department(DNO) --外键约束，之后再加
    PRIMARY KEY (SNO)
);


/*
部门 department
部门名称 dname
部门编号 dno
部门负责人工号 sno
*/

CREATE TABLE Department
(
    DNAME char(8) NOT NULL,
    DNO int NOT NULL,
    SNO int, -- FOREIGN KEY REFERENCES Staff(SNO) -- 外键约束，之后再加
    PRIMARY KEY (DNO)
);

/*
项目 project
项目名称 pname
项目编号 pno
主管部门编号 dno
*/

CREATE TABLE Project
(
    PNAME char(8) NOT NULL,
    PNO int NOT NULL,
    DNO int,
    PRIMARY KEY (PNO)
);

/*
工作 work
职工工号 sno
项目编号 pno
工作时间 time
*/

CREATE TABLE Work
(
    SNO int,
    PNO int NOT NULL,
    TIME int DEFAULT 0 -- 默认值约束
);

INSERT INTO Staff
VALUES 
('谢敏辉',131,21,-5,1),
('吴政亿',129,20,1000,2),
('吴玉明',130,21,100,3);

INSERT INTO Staff(SNAME,SNO,AGE,DNO)
VALUES
('吴一楠',128,21,3),
('许丽君',133,21,2);

INSERT INTO Department
VALUES 
('清洁工',1,131),
('指挥部',2,129),
('实践部',3,128);

INSERT INTO Department(DNAME,DNO)
VALUES
('挂名部',4);

INSERT INTO Project
VALUES
('图形学',1,2),
('JAVA',2,3),
('扫地',3,1);

INSERT INTO Project(PNAME,PNO)
VALUES
('划水',4);

INSERT INTO Work
VALUES
(129,2,10),
(129,1,5),
(128,2,2);

INSERT INTO Work(SNO,PNO)
VALUES
(131,3),
(130,4),
(133,4);

-- 1
SELECT * FROM Staff;
SELECT * FROM Project;
SELECT * FROM Department;
SELECT * FROM Work;

alter table Department
add constraint FK_DSNO
foreign key (SNO) 
references Staff(SNO)
ON UPDATE CASCADE;

alter table Project
add constraint FK_PDNO
foreign key (DNO) 
references Department(DNO)
ON UPDATE CASCADE;

alter table Work
add constraint FK_WSNO
foreign key (SNO) 
references Staff(SNO)
ON UPDATE CASCADE;

alter table Work
add constraint FK_WPNO
foreign key (PNO) 
references Project(PNO)
ON UPDATE CASCADE;

-- 测试：在父表上update记录时，同步update子表上外键的值
update Staff
set SNO = 127 where SNO=129;

-- 测试：当父表上记录被当做外键在子表中使用时，阻止对该记录的delete操作
DELETE FROM Staff WHERE SNO = 127; -- 违反外键约束
DELETE FROM Project WHERE PNO = 1; -- 违反外键约束
INSERT INTO Work VALUES (110,2,1000); -- 违反外键约束
INSERT INTO Project VALUES ('???',99,0); -- 违反外键约束
INSERT INTO Project VALUES ('emm',1, 2); -- 违反主键约束


delimiter //
CREATE TRIGGER TG_Time  
BEFORE INSERT ON Work  
FOR EACH ROW  
begin 
    if NEW.Time > 24 then 
        set NEW.Time = 24; 
    end if; 
end;
//

-- 触发器测试
SELECT * FROM Work;
INSERT INTO Work VALUES (131,2,200);
SELECT * FROM Work;
//

CREATE TRIGGER TG_salary  
AFTER INSERT ON Work  
FOR EACH ROW  
begin
    update Staff set salary = salary*1.02 where SNO=NEW.SNO;
    update Staff set salary = salary*1.03 where 
        SNO = any(SELECT SNO FROM Department);
end;
//

-- 触发器测试
SELECT * FROM Staff;
INSERT INTO Work VALUES (130,2,300); 
INSERT INTO Work VALUES (127,3,30);
SELECT * FROM Staff;
//

delimiter ; 

CREATE USER 'worker'@'localhost' IDENTIFIED BY '123456';

GRANT SELECT ON lab2.Staff TO 'worker'@'localhost';
GRANT UPDATE(AGE) ON lab2.Staff TO 'worker'@'localhost';

-- 下面进入worker

exit;
mysql -uworker -p123456
use lab2;

select * from staff;
select * from department;

update Staff
set AGE = 18 where SNO = 127;

update Staff
set SALARY = 1000000000 where SNO = 127;

select * from staff;

-- 下面返回root
exit;
mysql -uroot -pwuzhengyi

DROP USER 'worker'@'localhost';
DROP DATABASE lab2;