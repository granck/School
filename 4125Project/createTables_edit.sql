--------------------------------
-- Domain Constraint Tables --
--------------------------------

CREATE TABLE Difficulty_domain
(difficulty_id NUMBER(3),
  description VARCHAR(10),
  Primary KEY(difficulty_id)
);

CREATE TABLE Status_domain
(status_id NUMBER(3),
  description VARCHAR(10),
  PRIMARY KEY(status_id)
);

CREATE TABLE Pay_domain
(pay_id NUMBER(3),
  description VARCHAR(10),
  PRIMARY KEY(pay_id)
);

CREATE TABLE Format_domain
(format_id NUMBER(3),
  description VARCHAR(20),
  PRIMARY KEY(format_id)
);

CREATE TABLE Job_domain
(job_id NUMBER(3),
  description VARCHAR(10),
  PRIMARY KEY(job_id)
);

CREATE TABLE Gender_domain
(gender_id NUMBER(3),
  description VARCHAR(10),
  PRIMARY KEY(gender_id)
);

--------------------------------
--         Main Tables        --
--------------------------------
CREATE TABLE Person
(per_id NUMBER(10),
  name VARCHAR(20),
  address VARCHAR(50),
  zip_code NUMBER(5),
  email VARCHAR(30),
  gender NUMBER(5),
  phone NUMBER(10),
  PRIMARY KEY(per_id),
  FOREIGN KEY(gender) REFERENCES gender_domain(gender_id)
  );

CREATE TABLE Job_profile
(pos_code NUMBER(10),
  title VARCHAR(50),
  description VARCHAR(256),
  avg_pay NUMBER(8,2),
  PRIMARY KEY(pos_code)
);

--Company must be made before Job: foreign key reference
--otherwise: error "table or view does not exist"

CREATE TABLE Company
(comp_id NUMBER(10),
  address VARCHAR(50),
  zip_code NUMBER(5),
  primary_sector VARCHAR(20),
  speciality VARCHAR(20),
  website VARCHAR(20),
  PRIMARY KEY(comp_id)
);

CREATE TABLE Job
(job_code NUMBER(10),
  job_type NUMBER(9),
  pay_rate NUMBER(8,2),
  pay_type NUMBER(3),
  comp_id NUMBER(10),
  pos_code NUMBER(10),
  PRIMARY KEY(job_code),
  FOREIGN KEY(comp_id) REFERENCES Company(comp_id),
  FOREIGN KEY(pos_code) REFERENCES Job_profile(pos_code),
  FOREIGN KEY(job_type) REFERENCES Job_domain(job_id),
  FOREIGN KEY(pay_type) REFERENCES Pay_domain(pay_id)
  --CONSTRAINT job_type CHECK(job_type IN('full-time', 'part-time')),
  --CONSTRAINT pay_type CHECK(pay_type IN('wage', 'salary'))
  -- Every Job has to have a job Profile
);

CREATE TABLE Course
(c_code NUMBER(5),
  course_title VARCHAR(50),
  difficulty NUMBER(3),
  description VARCHAR(256),
  status NUMBER(3),
  --retail_price NUMBER(5,2),
  PRIMARY KEY(c_code),
  FOREIGN KEY(status) REFERENCES Status_domain(status_id),
  FOREIGN KEY(difficulty) REFERENCES Difficulty_domain(difficulty_id)
  --CONSTRAINT status CHECK(status IN ('active', 'expired')),
  --CONSTRAINT difficulty1 CHECK(difficulty IN ('beginner', 'medium', 'advanced'))
);

CREATE TABLE Section
(c_code NUMBER(5),
  sec_no NUMBER(3),
  complete_date DATE,
  year_off NUMBER(4),
  format_type NUMBER(3),
  price NUMBER(5,2),
  comp_id NUMBER(10),
  PRIMARY KEY(c_code, sec_no, year_off, comp_id),
  FOREIGN KEY(c_code) REFERENCES Course(c_code),
  FOREIGN KEY(comp_id) REFERENCES Company(comp_id),
  FOREIGN KEY(format_type) REFERENCES Format_domain(format_id)

  --CONSTRAINT format_type CHECK(format_type IN('classroom', 'online-sync', 'online-selfpaced', 'correspondence'))
);

CREATE TABLE Knowledge_Skill
(ks_code NUMBER(10),
  ks_name VARCHAR(20),
  description VARCHAR(256),
  difficulty NUMBER(3),
  PRIMARY KEY(ks_code),
  FOREIGN KEY(difficulty) REFERENCES Difficulty_domain(difficulty_id)

  --CONSTRAINT difficulty CHECK(difficulty IN('beginner', 'medium', 'advanced'))
);

------------------------------
--         Relations        --
------------------------------

CREATE TABLE Skills_required
(pos_code NUMBER(10),
  ks_code NUMBER(10),
  PRIMARY KEY(pos_code, ks_code),
  FOREIGN KEY(ks_code) REFERENCES Knowledge_Skill(ks_code),
  FOREIGN KEY(pos_code) REFERENCES Job_profile(pos_code)

  -- Is the relation between Job_profile and Knowledge_skill 
);

CREATE TABLE Skills_taught
(c_code NUMBER(5),
  ks_code NUMBER(10),
  PRIMARY KEY(c_code, ks_code),
  FOREIGN KEY(c_code) REFERENCES Course(c_code),
  FOREIGN KEY(ks_code) REFERENCES Knowledge_Skill(ks_code)

  -- relation between Knowledge_skill and Course
);

CREATE TABLE Skill_set
(per_id NUMBER(10),
  ks_code NUMBER(10),
  PRIMARY KEY(per_id, ks_code),
  FOREIGN KEY(per_id) REFERENCES Person(per_id),
  FOREIGN KEY(ks_code) REFERENCES Knowledge_Skill(ks_code)

 -- relation between Person and Knowledge_skill
);

CREATE TABLE Takes
(per_id NUMBER(10),
  c_code NUMBER(5),
  sec_no NUMBER(3),
  year_off NUMBER(4),
  comp_id NUMBER(10),
  PRIMARY KEY(per_id, c_code, sec_no, year_off, comp_id),
  FOREIGN KEY(per_id) REFERENCES Person(per_id),
  FOREIGN KEY(c_code, sec_no, year_off, comp_id) REFERENCES Section(c_code, sec_no, year_off, comp_id)
);

CREATE TABLE Works 
(work_id NUMBER(10),
  per_id NUMBER(10),
  job_code NUMBER(10),
  status NUMBER(3),
  pay_rate NUMBER(8,2),
  pay_type NUMBER(3),
  job_type NUMBER(3), 
  PRIMARY KEY(work_id),
  FOREIGN KEY(per_id) REFERENCES Person(per_id),
  FOREIGN KEY(job_code) REFERENCES Job(job_code),
  FOREIGN KEY(job_type) REFERENCES Job_domain(job_id),
  FOREIGN KEY(status) REFERENCES Status_domain(status_id),
  FOREIGN KEY(pay_type) REFERENCES Pay_domain(pay_id)

  --CONSTRAINT status2 CHECK(status IN('current', 'previous')),
  --CONSTRAINT job_type2 CHECK(job_type IN('full-time', 'part-time')),
  --CONSTRAINT pay_type2 CHECK(pay_type IN('wage', 'salary'))
  -- relation between Person and Job
);

CREATE TABLE temp_skill_set
(given_skill NUMBER(10),
  PRIMARY KEY(given_skill),
  FOREIGN KEY(given_skill) REFERENCES knowledge_skill(ks_code)
);

CREATE TABLE temp_skill_set2
(given_skill NUMBER(10),
  PRIMARY KEY(given_skill),
  FOREIGN KEY(given_skill) REFERENCES knowledge_skill(ks_code)
);

CREATE TABLE temp_skill_set3
(given_skill NUMBER(10),
  PRIMARY KEY(given_skill),
  FOREIGN KEY(given_skill) REFERENCES knowledge_skill(ks_code)
);

CREATE TABLE temp_skill_set4
(given_skill NUMBER(10),
  PRIMARY KEY(given_skill),
  FOREIGN KEY(given_skill) REFERENCES knowledge_skill(ks_code)
);
