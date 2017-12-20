CREATE DATABASE lab1;

USE lab1;

-- 1.1
CREATE TABLE Student
(
    SNO int NOT NULL,
    SNAME char(8) NOT NULL,
    SEX char(2),
    DEPTNO int,
    PRIMARY KEY (SNO)
);

-- 1.2
CREATE TABLE Course
(
    CNO int NOT NULL,
    CNAME char(20),
    TNO int NOT NULL,
    CREDIT int,
    CONSTRAINT CNO_TNO PRIMARY KEY (CNO,TNO)
);

-- 1.3
CREATE TABLE SC
(
    SNO int NOT NULL,
    CNO int NOT NULL,
    GRADE int,
    CONSTRAINT SNO_CNO PRIMARY KEY (SNO,CNO)
);

-- 1.4
CREATE TABLE Teacher
(
    TNO int NOT NULL,
    TNAME char(8) NOT NULL,
    DEPTNO int,
    PRIMARY KEY (TNO)
);

-- 1.5
CREATE TABLE Dept
(
    DEPTNO int NOT NULL,
    DNAME char(20) NOT NULL,
    PRIMARY KEY (DEPTNO)
);

-- 2.1
ALTER TABLE Student
ADD AGE SMALLINT;

-- ALTER TABLE Student
-- ALTER COLUMN AGE INT;
-- 这两句话报错 估计是版本不同吧

-- 2.2
ALTER TABLE Student 
MODIFY AGE INT NULL;

-- 3.1
INSERT INTO Student
VALUES 
(1001,'喵喵','m',10,20),
(1002,'汪汪','f',10,21),
(1003,'咩咩','m',10,21),
(1004,'哞哞','f',20,21),
(1005,'呱呱','m',20,22),
(1006,'嘎嘎','f',20,22),
(1007,'咕咕哒','f',30,20);

-- 3.2
INSERT INTO Course
VALUES
(1,'数据结构',101,4),
(2,'数据库',102,4),
(3,'离散数学',103,4),
(4,'C语言程序设计',101,2),
(5,'高等量子力学',105,3);

-- 3.3
INSERT INTO SC
VALUES
(1001,1,80),
(1001,2,85),
(1001,3,78),
(1002,1,72),
(1002,2,82),
(1002,3,86),
(1003,1,92),
(1003,3,90),
(1004,1,87),
(1004,4,90),
(1005,1,85),
(1005,4,92),
(1006,5,99),
(1006,2,100),
(1007,1,80),
(1007,3,91);

-- 3.4
INSERT INTO Teacher
VALUES
(101,'张小天',10),
(102,'胡小伟',10),
(103,'黄程',10),
(104,'郭冰',20),
(105,'钱祺',30);

-- 3.5
INSERT INTO Dept
VALUES
(10,'计算机'),
(20,'信管'),
(30,'物理');

-- 4.1
SELECT * 
FROM Student 
WHERE SEX = 'f';

-- 4.2
SELECT * 
FROM SC 
WHERE GRADE BETWEEN 80 AND 89 ORDER BY GRADE;

-- 4.3
SELECT DEPTNO,COUNT(1) AS NUMBER
FROM Student GROUP BY DEPTNO;

-- 5.1
SELECT Student.SNAME,Student.AGE
FROM Student,Dept
WHERE Student.DEPTNO = Dept.DEPTNO AND Student.AGE <= 21 AND Dept.DNAME = '信管' AND Student.SEX = 'f';

-- 6.1
SELECT SNAME 
FROM Student
WHERE SNO != ALL
(
    SELECT SC.SNO
    FROM SC
    LEFT JOIN 
    (
        SELECT Course.CNO, Course.CREDIT
        FROM Course,Teacher,Dept
        WHERE Course.TNO = Teacher.TNO AND Teacher.DEPTNO = Dept.DEPTNO AND Dept.DNAME = '计算机'
    ) c
    ON SC.CNO = c.CNO
    GROUP BY SC.SNO
    HAVING SUM(c.CREDIT) >= 5
);

-- 6.2
SELECT Student.SNAME, MAX(GRADE)
FROM Student,SC
WHERE Student.SNO = SC.SNO
GROUP BY CNO;

-- 6.3
SELECT DISTINCT SNO
FROM SC SCX
WHERE NOT EXISTS
(
    SELECT * 
    FROM SC SCY
    WHERE SCY.SNO = 1007 AND NOT EXISTS
    (
        SELECT * 
        FROM SC SCZ
        WHERE SCZ.SNO=SCX.SNO AND SCZ.CNO=SCY.CNO
    )
);

-- 6.4
SELECT DISTINCT SNAME
FROM SC SCX, Student
WHERE Student.SNO=SCX.SNO AND NOT EXISTS
(
    SELECT * 
    FROM SC SCY
    WHERE SCY.SNO = 1006 AND EXISTS
    (
        SELECT * 
        FROM SC SCZ
        WHERE SCZ.SNO=SCX.SNO AND SCZ.CNO=SCY.CNO
    )
);

-- 7.1
UPDATE SC 
SET GRADE = GRADE + 2 
WHERE EXISTS
(
    SELECT *
    FROM Course
    WHERE SC.CNO = Course.CNO AND Course.CNAME = '数据结构'
);

SELECT * FROM SC;

-- 8.1
DELETE FROM SC 
WHERE GRADE < 80 AND SNO IN
(
    SELECT SNO
    FROM Student
    WHERE SEX = 'f'
);

-- 9.1
CREATE VIEW CS_STUDENT AS
SELECT SNO, SNAME, SEX
FROM Student,Dept
WHERE Student.DEPTNO = Dept.DEPTNO AND Dept.DNAME = '计算机';

SELECT * FROM CS_STUDENT;

-- 9.2
DROP VIEW CS_STUDENT;

-- 10.1
DROP TABLE Student;

-- 10.2
DROP TABLE Course;

-- 10.3
DROP TABLE SC;

-- 10.4
DROP TABLE Teacher;

-- 10.5
DROP TABLE Dept;

DROP DATABASE LAB1;

