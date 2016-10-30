-- 1 Runs X
SELECT name
FROM Person NATURAL JOIN Works JOIN Job USING(job_code)
WHERE comp_id= 1234567890 AND status=003;

-- 2 Runs X
SELECT pay_rate
FROM Company NATURAL JOIN Job
WHERE pay_type=001 AND comp_id=1234567890
ORDER BY pay_rate DESC;

-- OR Runs X

SELECT name, works.pay_rate 
FROM Person NATURAL JOIN Works JOIN Job USING(job_code)
WHERE status=003 AND works.pay_type=001 AND comp_id=1234567890
ORDER BY works.pay_rate DESC;

-- 3 Runs X
WITH company_salary_total AS (
   SELECT comp_id, SUM(works.pay_rate) AS total_salary
   FROM works JOIN Job USING(job_code)
   WHERE works.pay_type=001 AND status=003
   GROUP BY comp_id), 
   
  company_wage_total AS (
   SELECT comp_id, SUM(works.pay_rate * 1920) AS total_wage
   FROM works JOIN Job USING(job_code)
   WHERE works.pay_type=002 AND status=003
   GROUP BY comp_id),
   
  company_salary_all AS(
    SELECT company.comp_id, total_salary
    FROM company LEFT OUTER JOIN 
      company_salary_total ON(company.comp_id = company_salary_total.comp_id))

SELECT CS.comp_id, SUM(NVL(total_salary,0) + NVL(total_wage,0)) AS labor_cost
FROM company_salary_all CS LEFT OUTER JOIN
    company_wage_total CW ON(CS.comp_id = CW.comp_id)
GROUP BY CS.comp_id
ORDER BY SUM(NVL(total_salary,0) + NVL(total_wage,0)) DESC;

-- 4 Runs X
SELECT job_code
FROM Works
WHERE status=003 AND per_id=1234567890;

-- 5 Runs X
SELECT ks_code, ks_name
FROM skill_set NATURAL JOIN Knowledge_skill
WHERE per_id=1234567890;

-- 6 Runs X
WITH persons_jobs AS (
   SELECT per_id, job_code, pos_code
   FROM Works JOIN Job USING(job_code)
   WHERE per_id=1234567890 and status=003)

SELECT job_code, ks_code
FROM persons_jobs NATURAL JOIN skills_required 
NATURAL JOIN ((SELECT ks_code 
              FROM skills_required) 
              MINUS 
              (SELECT ks_code 
              FROM skill_set
              WHERE per_id=1234567890))
ORDER BY job_code;

-- 7 Runs X
SELECT pos_code, ks_code AS job_skills, ks_name
FROM skills_required NATURAL JOIN Knowledge_skill
WHERE pos_code=1234567890;

-- 8 Runs X
SELECT DISTINCT ks_code, ks_name
FROM skills_required NATURAL JOIN Knowledge_skill
NATURAL JOIN ((SELECT ks_code 
              FROM skills_required
              WHERE pos_code=1230987654)
              MINUS 
              (SELECT ks_code 
              FROM skill_set
              WHERE per_id=1234567890));
-- 9 Runs X
WITH course_skill_set AS (
   SELECT c_code, ks_code
   FROM Course NATURAL JOIN Skills_taught)

SELECT c_code
FROM course c1
WHERE NOT EXISTS((SELECT given_skill 
                FROM temp_skill_set)
                MINUS
                (SELECT ks_code AS given_skill 
                FROM course_skill_set c2 
                WHERE c1.c_code = c2.c_code));

-- 10 Runs X
WITH missing_skills AS (
    SELECT ks_code
    FROM ( 
      SELECT ks_code
      FROM skills_required NATURAL JOIN job
      WHERE job_code=30030031
      MINUS
      SELECT ks_code
      FROM skill_set
      WHERE per_id=7))
  
  SELECT c_code, course_title
  FROM course
  WHERE c_code NOT IN(SELECT c_code
                      FROM(
                      SELECT ks_code, c_code
                      FROM missing_skills, course
                      MINUS
                      SELECT ks_code, c_code
                      FROM skills_taught));
   
-- 11 Runs X
WITH missing_skills AS (
    SELECT ks_code
    FROM ( 
      SELECT ks_code
      FROM skills_required NATURAL JOIN job
      WHERE job_code=30030031
      MINUS
      SELECT ks_code
      FROM skill_set
      WHERE per_id=7)),
  
  valid_courses AS (
    SELECT c_code, sec_no, comp_id, complete_date
    FROM course NATURAL JOIN section
    WHERE c_code NOT IN(SELECT c_code
                        FROM(
                        SELECT ks_code, c_code
                        FROM missing_skills, course
                        MINUS
                        SELECT ks_code, c_code
                        FROM skills_taught))),
    
  closest_date AS(
    SELECT MIN(complete_date) as complete_date
    FROM valid_courses)
    
SELECT c_code, sec_no, comp_id, complete_date
FROM valid_courses NATURAL JOIN closest_date;

-- 12
DROP TABLE CourseSet;
DROP SEQUENCE CourseSet_seq;

CREATE SEQUENCE CourseSet_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 999999
NOCYCLE;

CREATE TABLE CourseSet (
csetID NUMBER(8, 0) PRIMARY KEY,
c_code1 NUMBER(5, 0), c_code2 NUMBER(5, 0), c_code3 NUMBER(5, 0),
num NUMBER(2, 0)
);
/* two-course set */
INSERT INTO CourseSet
SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, null, 2
FROM Course C1, Course C2
WHERE C1.c_code < C2.c_code;
/* three-course set */
INSERT INTO CourseSet
SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, C3.c_code, 3
FROM Course C1, Course C2, Course C3
WHERE C1.c_code < C2.c_code AND C2.c_code < C3.c_code;

/* the relationship between course set and its covering skills */
WITH CourseSet_Skill(csetID, ks_code) AS (
    SELECT csetID, ks_code
    FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code1=CS.c_code
    UNION
    SELECT csetID, ks_code
    FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code2=CS.c_code
    UNION
    SELECT csetID, ks_code
    FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code3=CS.c_code), 

/* use division to find those course sets that cover missing skills */
  Cover_CSet(csetID, num) AS (
    SELECT csetID, num
    FROM CourseSet CSet
    WHERE NOT EXISTS (SELECT ks_code
                      FROM temp_table
                      MINUS
                      SELECT ks_code
                      FROM CourseSet_Skill CSSk
                      WHERE CSSk.csetID = Cset.csetID))
                      
  /* to find the smallest sets */
SELECT c_code1, c_code2, c_code3
FROM Cover_CSet NATURAL JOIN CourseSet
WHERE num =(SELECT MIN(num) 
            FROM Cover_CSet); 

-- 13 Runs X
DROP TABLE CourseSet;
DROP SEQUENCE CourseSet_seq;

CREATE SEQUENCE CourseSet_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 999999
NOCYCLE;

CREATE TABLE CourseSet (
csetID NUMBER(8, 0) PRIMARY KEY,
c_code1 NUMBER(5, 0), c_code2 NUMBER(5, 0), c_code3 NUMBER(5, 0),
num NUMBER(2, 0)
);
/* two-course set */
INSERT INTO CourseSet
SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, null, 2
FROM Course C1, Course C2
WHERE C1.c_code < C2.c_code;
/* three-course set */
INSERT INTO CourseSet
SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, C3.c_code, 3
FROM Course C1, Course C2, Course C3
WHERE C1.c_code < C2.c_code AND C2.c_code < C3.c_code; 

/* the relationship between course set and its covering skills */
WITH CourseSet_Skill(csetID, ks_code) AS (
    SELECT csetID, ks_code
    FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code1=CS.c_code
    UNION
    SELECT csetID, ks_code
    FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code2=CS.c_code
    UNION
    SELECT csetID, ks_code
    FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code3=CS.c_code), 

  missing_skills AS (
    SELECT ks_code
    FROM knowledge_skill
    NATURAL JOIN (SELECT ks_code
                  FROM skills_required NATURAL JOIN job
                  WHERE job_code=5432198760
                  MINUS
                  SELECT ks_code
                  FROM skill_set
                  WHERE per_id=1234567890)),

/* use division to find those course sets that cover missing skills */
  Cover_CSet(csetID, num) AS (
    SELECT csetID, num
    FROM CourseSet CSet
    WHERE NOT EXISTS (SELECT ks_code
                      FROM missing_skills
                      MINUS
                      SELECT ks_code
                      FROM CourseSet_Skill CSSk
                      WHERE CSSk.csetID = Cset.csetID))
                      
  /* to find the smallest sets */
SELECT c_code1, c_code2, c_code3
FROM Cover_CSet NATURAL JOIN CourseSet
WHERE num =(SELECT MIN(num) 
            FROM Cover_CSet); 
-- 14 No can skip
-- 15 Runs X
SELECT DISTINCT pos_code
FROM job_profile p1
WHERE NOT EXISTS ((SELECT ks_code
                  FROM skills_required s2
                  WHERE p1.pos_code=s2.pos_code)
                  MINUS 
                  (SELECT ks_code
                  FROM skill_set
                  WHERE per_id = 779966655));

-- 16 Runs X
WITH unqualified_positions AS (
  SELECT DISTINCT pos_code
  FROM skills_required NATURAL JOIN ((SELECT ks_code
                                      FROM skills_required)
                                      MINUS
                                      (SELECT ks_code
                                      FROM skill_set
                                      WHERE per_id=779966655))),
  total_pay AS (
    SELECT job_code, 
      CASE
        WHEN pay_type=002 THEN pay_rate * 1920 
        WHEN pay_type=001 THEN pay_rate
      END AS total_earnings
    FROM job NATURAL JOIN ((SELECT pos_code
                            FROM job)
                            MINUS
                            (SELECT pos_code
                            FROM unqualified_positions)))
                        
SELECT job_code, total_earnings
FROM total_pay
WHERE total_earnings >= ALL (SELECT total_earnings
                            FROM total_pay);
-- 17 -- runs X
SELECT name, email
FROM person 
WHERE NOT EXISTS ((SELECT ks_code
                  FROM skills_required
                  WHERE pos_code=8749348561)
                  MINUS
                 (SELECT ks_code 
                  FROM skill_set
                  WHERE skill_set.per_id=person.per_id));

-- 18 -- X
--We have no per_id that have fewer than 3 missing ks_code
WITH missing_skills AS(
  SELECT per_id, ks_code
  FROM Skills_required, person
  WHERE pos_code = 2200440066
  MINUS
  SELECT per_id, ks_code
  FROM skill_set, job_profile
  WHERE pos_code = 2200440066),
  
  missing_skill_count AS(
    SELECT per_id, COUNT(ks_code) AS num_of_missing_skills
    FROM missing_skills
    GROUP BY per_id
    ORDER BY num_of_missing_skills)
    
SELECT per_id, num_of_missing_skills
FROM missing_skill_count
WHERE num_of_missing_skills = 1;

-- 19 Runs --X
WITH missing_skills AS(
  SELECT per_id, ks_code
  FROM Skills_required, person
  WHERE pos_code = 2200440066
  MINUS
  SELECT per_id, ks_code
  FROM skill_set, job_profile
  WHERE pos_code = 2200440066),
  
  missing_skill_count AS(
    SELECT per_id, COUNT(ks_code) AS num_of_missing_skills
    FROM missing_skills
    GROUP BY per_id
    ORDER BY num_of_missing_skills),
  
  people_missing_skills AS(
    SELECT per_id, num_of_missing_skills
    FROM missing_skill_count
    WHERE num_of_missing_skills = 1)
    
Select ks_code, COUNT(per_id) AS people_missing
FROM missing_skills NATURAL JOIN people_missing_skills
GROUP BY ks_code
ORDER BY people_missing;

-- 20 Runs -- X
WITH missing_skills AS(
  SELECT per_id, ks_code
  FROM Skills_required, person
  WHERE pos_code = 2200440066
  MINUS
  SELECT per_id, ks_code
  FROM skill_set, job_profile
  WHERE pos_code = 2200440066),
  
  missing_skill_count AS(
    SELECT per_id, COUNT(ks_code) AS num_of_missing_skills
    FROM missing_skills
    GROUP BY per_id
    ORDER BY num_of_missing_skills),
    
  least_skills_missing AS(
    SELECT num_of_missing_skills
    FROM missing_skill_count
    WHERE ROWNUM = 1)

SELECT per_id, num_of_missing_skills
FROM missing_skill_count NATURAL JOIN least_skills_missing;

-- 21 Runs -- X
WITH missing_skills AS(
  SELECT per_id, ks_code
  FROM Skills_required, person
  WHERE pos_code = 2200440066
  MINUS
  SELECT per_id, ks_code
  FROM skill_set, job_profile
  WHERE pos_code = 2200440066),
  
  missing_skill_count AS(
    SELECT per_id, COUNT(ks_code) AS num_of_missing_skills
    FROM missing_skills
    GROUP BY per_id
    ORDER BY num_of_missing_skills)
    
SELECT per_id, num_of_missing_skills
FROM missing_skill_count
WHERE num_of_missing_skills < 4;
--WHERE num_of_missing_skills <= k;

-- 22 Runs -- X
WITH missing_skills AS(
  SELECT per_id, ks_code
  FROM Skills_required, person
  WHERE pos_code = 2200440066
  MINUS
  SELECT per_id, ks_code
  FROM skill_set, job_profile
  WHERE pos_code = 2200440066),
  
  missing_skill_count AS(
    SELECT per_id, COUNT(ks_code) AS num_of_missing_skills
    FROM missing_skills
    GROUP BY per_id
    ORDER BY num_of_missing_skills),
  
  people_missing_skills AS(
    SELECT per_id, num_of_missing_skills
    FROM missing_skill_count
    WHERE num_of_missing_skills < 5)

Select ks_code, COUNT(per_id) AS people_missing
FROM missing_skills NATURAL JOIN people_missing_skills
GROUP BY ks_code
ORDER BY people_missing;

-- 23 Runs -- X
SELECT per_id, name, address, zip_code, email, gender, phone
FROM person NATURAL JOIN works JOIN job USING(job_code)
WHERE pos_code=1234567890; --provided by user

-- 24 Runs X       
WITH unemployeed AS(
  SELECT per_id, name, address, zip_code, email, gender, phone, job_code
  FROM person NATURAL JOIN (SELECT per_id, job_code
                                FROM works
                                WHERE status=004
                                MINUS
                                SELECT per_id, job_code
                                FROM works
                                WHERE status = 003))
  
SELECT per_id, name, address, zip_code, email, gender, phone
FROM unemployeed NATURAL JOIN job
WHERE pos_code=2200440066;

--25 Runs -- X
WITH company_employees AS(
  SELECT comp_id, COUNT(per_id) AS employee_count
  FROM works JOIN job USING(job_code)
  WHERE status=003
  GROUP BY comp_id
  ORDER BY employee_count DESC),
  
  company_salary_total AS (
   SELECT comp_id, SUM(pay_rate) AS total_salary
   FROM works NATURAL JOIN Company NATURAL JOIN Job
   WHERE pay_type=001 AND status = 003
   GROUP BY comp_id), 
   
  company_wage_total AS (
   SELECT comp_id, SUM(pay_rate * 1920) AS total_wage
   FROM works NATURAL JOIN Company NATURAL JOIN Job
   WHERE pay_type=002 AND status = 003
   GROUP BY comp_id),
   
  company_salary_all AS(
    SELECT company.comp_id, total_salary
    FROM company LEFT OUTER JOIN 
      company_salary_total ON(company.comp_id = company_salary_total.comp_id)),
      
  company_wage_salary_all AS(
    Select company_salary_all.comp_id, total_salary, total_wage
    From company_salary_all LEFT OUTER JOIN
      company_wage_total ON(company_salary_all.comp_id = company_wage_total.comp_id)),

  company_labor_cost AS(
    SELECT comp_id, SUM(NVL(total_salary,0) + NVL(total_wage,0)) AS labor_cost
    FROM company_wage_salary_all
    GROUP BY comp_id
    ORDER BY SUM(NVL(total_salary,0) + NVL(total_wage,0)) DESC)

SELECT company_employees.comp_id AS largest_employeer, employee_count,
  company_labor_cost.comp_id AS largest_cost, labor_cost
FROM company_employees, company_labor_cost
WHERE ROWNUM = 1;

-- 26 27 are grad questions
-- 28 29 can skip

-- 30 Runs
WITH missing_skills AS(
    SELECT DISTINCT ks_code
    FROM ((SELECT ks_code
          FROM skills_required
          WHERE pos_code=5432198760)
          MINUS
          (SELECT ks_code
          FROM skill_set
          WHERE per_id=1234567890)))
                                
SELECT DISTINCT c_code
FROM skills_taught NATURAL JOIN missing_skills;
