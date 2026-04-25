-- ============================================================
-- Placement Preparation Portal - MySQL Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS placement_portal;
USE placement_portal;

-- ============================================================
-- 1. USERS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100)        NOT NULL,
    email       VARCHAR(150)        NOT NULL UNIQUE,
    password    VARCHAR(255)        NOT NULL,   -- BCrypt hashed
    college     VARCHAR(200),
    branch      VARCHAR(100),
    year        TINYINT,
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    is_active   BOOLEAN             DEFAULT TRUE
);

-- ============================================================
-- 2. ADMINS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS admins (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(80)         NOT NULL UNIQUE,
    password    VARCHAR(255)        NOT NULL,   -- BCrypt hashed
    email       VARCHAR(150)        NOT NULL UNIQUE,
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 3. SUBJECTS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS subjects (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)        NOT NULL,
    description TEXT,
    icon        VARCHAR(50)         DEFAULT 'book',
    color       VARCHAR(20)         DEFAULT '#3b82f6',
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 4. TOPICS TABLE (belongs to subject)
-- ============================================================
CREATE TABLE IF NOT EXISTS topics (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    subject_id  INT                 NOT NULL,
    title       VARCHAR(200)        NOT NULL,
    content     TEXT,
    resource_url VARCHAR(500),
    difficulty  ENUM('Easy','Medium','Hard') DEFAULT 'Medium',
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- ============================================================
-- 5. USER_TOPIC_PROGRESS (marks topics as completed per user)
-- ============================================================
CREATE TABLE IF NOT EXISTS user_topic_progress (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT                 NOT NULL,
    topic_id    INT                 NOT NULL,
    completed   BOOLEAN             DEFAULT FALSE,
    completed_at TIMESTAMP          NULL,
    UNIQUE KEY uq_user_topic (user_id, topic_id),
    FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE
);

-- ============================================================
-- 6. TASKS (TO-DO List)
-- ============================================================
CREATE TABLE IF NOT EXISTS tasks (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT                 NOT NULL,
    title       VARCHAR(300)        NOT NULL,
    description TEXT,
    due_date    DATE,
    priority    ENUM('Low','Medium','High') DEFAULT 'Medium',
    completed   BOOLEAN             DEFAULT FALSE,
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- 7. MOCK TESTS
-- ============================================================
CREATE TABLE IF NOT EXISTS mock_tests (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200)        NOT NULL,
    subject_id  INT,
    duration    INT                 DEFAULT 30,  -- minutes
    total_marks INT                 DEFAULT 10,
    description TEXT,
    is_active   BOOLEAN             DEFAULT TRUE,
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE SET NULL
);

-- ============================================================
-- 8. QUESTIONS (MCQ)
-- ============================================================
CREATE TABLE IF NOT EXISTS questions (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    test_id     INT                 NOT NULL,
    question    TEXT                NOT NULL,
    option_a    VARCHAR(500)        NOT NULL,
    option_b    VARCHAR(500)        NOT NULL,
    option_c    VARCHAR(500)        NOT NULL,
    option_d    VARCHAR(500)        NOT NULL,
    correct_ans CHAR(1)             NOT NULL,   -- 'A','B','C','D'
    marks       INT                 DEFAULT 1,
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (test_id) REFERENCES mock_tests(id) ON DELETE CASCADE
);

-- ============================================================
-- 9. TEST RESULTS
-- ============================================================
CREATE TABLE IF NOT EXISTS test_results (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT                 NOT NULL,
    test_id     INT                 NOT NULL,
    score       INT                 DEFAULT 0,
    total_marks INT                 DEFAULT 0,
    time_taken  INT,                            -- seconds
    attempted_at TIMESTAMP          DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (test_id) REFERENCES mock_tests(id) ON DELETE CASCADE
);

-- ============================================================
-- 10. JOBS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS jobs (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(200)        NOT NULL,
    company      VARCHAR(200)        NOT NULL,
    location     VARCHAR(150),
    type         ENUM('Full-Time','Internship','Part-Time','Remote') DEFAULT 'Full-Time',
    package      VARCHAR(100),
    description  TEXT,
    requirements TEXT,
    apply_link   VARCHAR(500),
    deadline     DATE,
    is_active    BOOLEAN             DEFAULT TRUE,
    created_at   TIMESTAMP           DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 11. JOB APPLICATIONS
-- ============================================================
CREATE TABLE IF NOT EXISTS job_applications (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT                 NOT NULL,
    job_id      INT                 NOT NULL,
    status      ENUM('Applied','Under Review','Selected','Rejected') DEFAULT 'Applied',
    applied_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_user_job (user_id, job_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (job_id)  REFERENCES jobs(id)  ON DELETE CASCADE
);

-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Default Admin (password: Admin@123)
INSERT INTO admins (username, password, email) VALUES
('admin', '$2a$12$K7UwaM7A03y0IlLjheBsFOSIKb87lqbGEitufgXwIBW7hx1xg1m8C', 'admin@placement.com');

-- Subjects
INSERT INTO subjects (name, description, icon, color) VALUES
('Aptitude',          'Quantitative, Logical & Verbal Reasoning',       'calculator',  '#f59e0b'),
('Data Structures',   'Arrays, Trees, Graphs, Stacks, Queues & more',   'code',        '#3b82f6'),
('Algorithms',        'Sorting, Searching, DP, Greedy & Graph algos',   'cpu',         '#8b5cf6'),
('Core CS',           'OS, DBMS, CN, OOP Concepts',                     'server',      '#10b981'),
('System Design',     'HLD, LLD, Scalability & Architecture',           'layers',      '#ef4444'),
('Interview Prep',    'HR Questions, Resume Tips, Communication',        'users',       '#06b6d4');

-- Topics for Aptitude
INSERT INTO topics (subject_id, title, content, resource_url, difficulty) VALUES
(1, 'Number System',        'Divisibility, HCF, LCM, factors and multiples.',     'https://www.indiabix.com/aptitude/numbers/', 'Easy'),
(1, 'Percentages',          'Basic percentage calculations and applications.',    'https://www.indiabix.com/aptitude/percentage/', 'Easy'),
(1, 'Profit & Loss',        'Cost price, selling price, profit/loss calculations.','https://www.indiabix.com/aptitude/profit-and-loss/', 'Medium'),
(1, 'Time & Work',          'Work efficiency, pipes and cisterns.',               'https://www.indiabix.com/aptitude/time-and-work/', 'Medium'),
(1, 'Logical Reasoning',    'Syllogisms, blood relations, puzzles.',              'https://www.indiabix.com/logical-reasoning/', 'Hard');

-- Topics for DSA
INSERT INTO topics (subject_id, title, content, resource_url, difficulty) VALUES
(2, 'Arrays & Strings',     'Traversal, sliding window, two pointer techniques.', 'https://leetcode.com/tag/array/', 'Easy'),
(2, 'Linked Lists',         'Singly, doubly, circular linked lists, reversal.',   'https://leetcode.com/tag/linked-list/', 'Medium'),
(2, 'Stacks & Queues',      'LIFO/FIFO, applications, monotonic stacks.',         'https://leetcode.com/tag/stack/', 'Medium'),
(2, 'Trees & BST',          'Traversals, height, lowest common ancestor, BST.',   'https://leetcode.com/tag/tree/', 'Hard'),
(2, 'Graphs',               'BFS, DFS, shortest path, topological sort.',         'https://leetcode.com/tag/graph/', 'Hard');

-- Topics for Algorithms
INSERT INTO topics (subject_id, title, content, resource_url, difficulty) VALUES
(3, 'Sorting Algorithms',   'Bubble, Selection, Merge, Quick, Heap Sort.',        'https://visualgo.net/en/sorting', 'Easy'),
(3, 'Dynamic Programming',  'Memoization, tabulation, classic DP problems.',      'https://leetcode.com/tag/dynamic-programming/', 'Hard'),
(3, 'Greedy Algorithms',    'Activity selection, fractional knapsack.',           'https://www.geeksforgeeks.org/greedy-algorithms/', 'Medium'),
(3, 'Binary Search',        'Search on monotone functions, variants.',            'https://leetcode.com/tag/binary-search/', 'Medium');

-- Topics for Core CS
INSERT INTO topics (subject_id, title, content, resource_url, difficulty) VALUES
(4, 'Operating Systems',    'Processes, threads, scheduling, deadlock, memory.',  'https://www.geeksforgeeks.org/operating-systems/', 'Medium'),
(4, 'DBMS Fundamentals',    'ER model, normalization, SQL, transactions, ACID.',  'https://www.geeksforgeeks.org/dbms/', 'Medium'),
(4, 'Computer Networks',    'OSI model, TCP/IP, HTTP, DNS, routing.',             'https://www.geeksforgeeks.org/computer-network-tutorials/', 'Medium'),
(4, 'OOP Concepts',         'Encapsulation, inheritance, polymorphism, SOLID.',   'https://www.geeksforgeeks.org/object-oriented-programming-oops-concept-in-java/', 'Easy');

-- Mock Tests
INSERT INTO mock_tests (title, subject_id, duration, total_marks, description) VALUES
('Aptitude Basics', 1, 20, 10, 'Basic quantitative and logical aptitude test.'),
('DSA Fundamentals', 2, 30, 10, 'Test on arrays, linked lists and basic data structures.'),
('Core CS Quiz',    4, 25, 10, 'OS, DBMS and Networks quick quiz.');

-- Questions for Aptitude Basics (test_id=1)
INSERT INTO questions (test_id, question, option_a, option_b, option_c, option_d, correct_ans, marks) VALUES
(1,'What is 15% of 240?',                          '32','36','40','44',                        'B', 1),
(1,'HCF of 12 and 18 is?',                         '4','6','9','12',                           'B', 1),
(1,'A train travels 300 km in 5 hours. Speed?',    '50 km/h','55 km/h','60 km/h','65 km/h',   'C', 1),
(1,'If A=2, B=3, then A²+B² = ?',                  '11','12','13','14',                        'C', 1),
(1,'What is 25% of 400?',                          '80','90','100','110',                      'C', 1),
(1,'LCM of 6 and 8 is?',                           '16','18','24','48',                        'C', 1),
(1,'Simple interest on ₹1000 at 10% for 2 years?','₹150','₹180','₹200','₹220',               'C', 1),
(1,'Next in series: 2, 6, 12, 20, __?',            '28','30','36','40',                        'B', 1),
(1,'If MANGO is coded as NBOIP, how is APPLE?',    'BQQMF','BQPMF','CQQMF','BQQNF',           'A', 1),
(1,'Which is largest? 2/3, 3/4, 4/5, 5/6',        '2/3','3/4','4/5','5/6',                   'D', 1);

-- Questions for DSA Fundamentals (test_id=2)
INSERT INTO questions (test_id, question, option_a, option_b, option_c, option_d, correct_ans, marks) VALUES
(2,'Time complexity of binary search?',                    'O(n)','O(log n)','O(n²)','O(1)',              'B', 1),
(2,'Which data structure uses LIFO?',                      'Queue','Stack','Deque','Heap',                'B', 1),
(2,'Array index starts from?',                             '1','0','-1','Depends on language',            'B', 1),
(2,'Linked list node contains?',                           'Data only','Pointer only','Data & pointer','Index', 'C', 1),
(2,'BFS uses which data structure?',                       'Stack','Queue','Tree','Graph',                'B', 1),
(2,'Which sorting is O(n log n) worst case?',              'Quick sort','Bubble sort','Merge sort','Insertion sort', 'C', 1),
(2,'Height of a balanced BST with n nodes?',               'O(n)','O(log n)','O(n²)','O(1)',             'B', 1),
(2,'Inorder traversal of BST gives?',                      'Random order','Sorted order','Reverse sorted','Level order', 'B', 1),
(2,'Stack overflow is caused by?',                         'Infinite loops','Deep recursion','Large arrays','All of above', 'B', 1),
(2,'Which is not a linear data structure?',                'Array','Queue','Tree','Stack',                'C', 1);

-- Questions for Core CS Quiz (test_id=3)
INSERT INTO questions (test_id, question, option_a, option_b, option_c, option_d, correct_ans, marks) VALUES
(3,'Which scheduling algo has minimum avg waiting time?','FCFS','SJF','Round Robin','Priority',          'B', 1),
(3,'ACID stands for?','Atom/Consist/Isolated/Durable','Atomic/Consist/Isolated/Durable','Atomic/Consist/Integral/Durable','Atomic/Complete/Isolated/Durable', 'B', 1),
(3,'OSI model has how many layers?',                    '5','6','7','8',                                 'C', 1),
(3,'Which is not a feature of OOP?',                    'Polymorphism','Inheritance','Recursion','Encapsulation', 'C', 1),
(3,'Deadlock requires which condition?',                'Mutual exclusion','Hold and wait','No preemption','All of the above', 'D', 1),
(3,'HTTP default port is?',                             '21','443','80','8080',                          'C', 1),
(3,'SQL command to retrieve data?',                     'INSERT','UPDATE','SELECT','DELETE',             'C', 1),
(3,'Virtual memory technique?',                         'Paging','Swapping','Segmentation','All of above', 'D', 1),
(3,'Which normal form removes transitive dependency?',  '1NF','2NF','3NF','BCNF',                       'C', 1),
(3,'TCP is?',                                           'Connectionless','Connection-oriented','Both','Neither', 'B', 1);

-- Sample Jobs
INSERT INTO jobs (title, company, location, type, package, description, requirements, apply_link, deadline) VALUES
('Software Engineer Intern',  'TCS Digital',        'Hyderabad', 'Internship', '₹15,000/month', 'Work on live projects with experienced developers.', 'B.E/B.Tech CSE/IT, CGPA >= 7.0, Java or Python knowledge.', 'https://tcs.com/careers', '2025-07-31'),
('Junior Developer',          'Infosys',            'Pune',      'Full-Time',  '₹4.5 LPA',      'Join our digital transformation team.', 'BE/BTech, Strong OOP skills, Good communication.', 'https://infosys.com/careers', '2025-06-30'),
('Backend Engineer',          'Wipro',              'Bangalore', 'Full-Time',  '₹5 LPA',        'Build scalable backend services.', 'Java/Spring Boot, MySQL/PostgreSQL, REST APIs.', 'https://wipro.com/careers', '2025-08-15'),
('Frontend Developer',        'HCL Technologies',  'Chennai',   'Full-Time',  '₹4 LPA',        'Build responsive web applications.', 'HTML, CSS, JavaScript, React basics.', 'https://hcltech.com/careers', '2025-07-15'),
('Data Science Intern',       'Cognizant',          'Remote',    'Internship', '₹12,000/month', 'Work on ML models and data pipelines.', 'Python, NumPy, Pandas, basic ML knowledge.', 'https://cognizant.com/careers', '2025-06-15'),
('Full Stack Developer',      'Tech Mahindra',      'Hyderabad', 'Full-Time',  '₹6 LPA',        'End-to-end web application development.', '2+ years exp OR strong project portfolio, Java, React.', 'https://techmahindra.com/careers', '2025-09-01');
