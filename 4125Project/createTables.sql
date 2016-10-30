--------------------------------
--         Main Tables        --
--------------------------------
CREATE TABLE Person
(per_id VARCHAR(10),
  name VARCHAR(20),
  address VARCHAR(50),
  zip_code INT(5),
  email VARCHAR(30),
  gender VARCHAR(6),
  phone INT(10),
  PRIMARY KEY(per_id)
  );

CREATE TABLE Job_profile
(pos_code VARCHAR(10),
  title VARCHAR(50),
  description VARCHAR(256),
  avg_pay NUMERIC(8,2),
  PRIMARY KEY(pos_code),
);

CREATE TABLE Job
(job_code VARCHAR(10),
  type VARCHAR(9),
  pay_rate NUMERIC(8,2),
  pay_type VARCHAR(6),
  company VARCHAR(10),
  PRIMARY KEY(job_code),
  FOREIGN KEY(company) REFERENCES Company(comp_id)

  CONSTRAINT type-in-domain-test CHECK(type IN('full-time', 'part-time'))
  CONSTRAINT pay-type-in-domain-test CHECK(pay_type IN('wage', 'salary'))
  -- Every Job has to have a job Profile
);

CREATE TABLE Company
(comp_id VARCHAR(10),
  address VARCHAR(50),
  zip_code INT(5),
  primary_sector VARCHAR(20),
  speciality VARCHAR(20),
  website VARCHAR(20),
  PRIMARY KEY(comp_id)
);

CREATE TABLE Course
(c_code INT(5),
  course_title VARCHAR(50),
  level VARCHAR(20),
  description VARCHAR(256),
  status VARCHAR(7),
  retail_price NUMERIC(5,2),
  PRIMARY KEY(c_code)

  CONSTRAINT status-in-domain-test CHECK(status IN ('active', 'expired'))
);

CREATE TABLE Section
(c_code INT(5),
  sec_no INT(3),
  complete_date DATE,
  year INT(4),
  format VARCHAR(20),
  retail_price NUMERIC(5,2),
  PRIMARY KEY(c_code, sec_no, year),
  FOREIGN KEY(c_code) REFERENCES Course(c_code)

  CONSTRAINT format-in-domain-test CHECK(format IN('classroom', 'online-sync', 'online-selfpaced', 'correspondence'))
);

CREATE TABLE Knowledge_Skill
(ks_code VARCHAR(10),
  ks_name VARCHAR(20),
  description VARCHAR(256),
  level VARCHAR(10),
  PRIMARY KEY(ks_code)

  CONSTRAINT level-in-domain-test CHECK(level IN('beginner', 'medium', 'advanced'))
);

------------------------------
--         Relations        --
------------------------------

CREATE TABLE Skills_required
(pos_code VARCHAR(10),
  required_skills VARCHAR(10),
  PRIMARY KEY(pos_code),
  FOREIGN KEY(required_skill) REFERENCES Knowledge_Skill(ks_code)

  -- Is the relation between Job_profile and Knowledge_skill 
);

CREATE TABLE Skills_taught
(c_code INT(5),
  ks_code VARCHAR(10),
  PRIMARY KEY(c_code),
  FOREIGN KEY(c_code) REFERENCES Course(c_code),
  FOREIGN KEY(ks_code) REFERENCES Knowledge_Skill(ks_code)

  -- relation between Knowledge_skill and Course
);

CREATE TABLE Skill_set
(per_id VARCHAR(10),
  ks_code VARCHAR(10),
  FOREIGN KEY(per_id) REFERENCES Person(per_id),
  FOREIGN KEY(ks_code) REFERENCES Knowledge_Skill(ks_code)

 -- relation between Person and Knowledge
);

CREATE TABLE Works 
(per_id VARCHAR(10),
  job_code VARCHAR(10),
  status VARCHAR(8),
  pay_rate NUMERIC(8,2),
  pay_type VARCHAR(6),
  type VARCHAR(9), 
  PRIMARY KEY(per_id, job_code),
  FOREIGN KEY(per_id) REFERENCES Person(per_id),
  FOREIGN KEY(job_code) REFERENCES Job(job_code)

  CONSTRAINT status-in-domain-test CHECK(status IN('current', 'previous'))
  CONSTRAINT type-in-domain-test CHECK(type IN('full-time', 'part-time'))
  CONSTRAINT pay-type-in-domain-test CHECK(pay_type IN('wage', 'salary'))
  -- relation between Person and Job
);

CREATE TABLE Job_profile_Jobs -- completely open to renaming
(pos_code VARCHAR(10),
  job_code VARCHAR(10),
  PRIMARY KEY(pos_code),
  FOREIGN KEY(pos_code) REFERENCES Job_profile(pos_code),
  FOREIGN KEY(job_code) REFERENCES Job(job_code)

  -- Relation between Job_profile and Job
);

CREATE TABLE Course_Sections -- Should look closer at Course_sections and Offers
(c_code INT(5),
  sec_no INT(3),
  year INT(4),
  PRIMARY KEY(c_code),
  FOREIGN KEY(c_code) REFERENCES Section(c_code),
  FOREIGN KEY(sec_no) REFERENCES Section(sec_no),
  FOREIGN KEY(year) REFERENCES Section(year)

  -- Could add constraint to limit the year, lest someone takes a course in the year -9999
  ------ This feels extraneous as well, do we want to leave it as natural joins, or 
  ------ have the information repeated
  -- relation between Course and Section
);

CREATE TABLE Offers
(comp_id VARCHAR(10),
  c_code INT(5),
  sec_no INT(3),
  year INT(4)
  PRIMARY KEY(comp_id),
  FOREIGN KEY(c_code) REFERENCES Section(c_code),
  FOREIGN KEY(sec_no) REFERENCES Section(sec_no),
  FOREIGN KEY(year) REFERENCES Section(year),
  FOREIGN KEY(offered_by) REFERENCES Company(comp_id)

  -- relation between Company and Section
);


