USE [CodeCademy]
GO

-- Test data for Certificate table
INSERT INTO [dbo].[Certificate] ([CertificateID], [Grade], [ApproverName], [CursistEmailAddress], [CourseName])
VALUES
    (1, 90, 'John Doe', 'cursist1@example.com', 'Programming 101'),
    (2, 75, 'Jane Smith', 'cursist2@example.com', 'Web Development Basics'),
    (3, 85, 'Bob Johnson', 'cursist3@example.com', 'Database Fundamentals'),
    (4, 95, 'Alice Brown', 'cursist4@example.com', 'Java Programming'),
    (5, 80, 'Charlie Green', 'cursist5@example.com', 'Python Basics'),
    (6, 92, 'David White', 'cursist6@example.com', 'Web Design Essentials'),
    (7, 78, 'Eva Black', 'cursist7@example.com', 'SQL Mastery'),
    (8, 88, 'Frank Grey', 'cursist8@example.com', 'C# Development'),
    (9, 91, 'Grace Red', 'cursist9@example.com', 'JavaScript Advanced'),
    (10, 83, 'Henry Blue', 'cursist10@example.com', 'Data Science Intro');

-- Test data for ContentItem table
INSERT INTO [dbo].[ContentItem] ([ContentItemID], [PublicationDate], [Status])
VALUES
    (1, '2024-01-12', 'Published'),
    (2, '2024-01-10', 'Draft'),
    (3, '2024-01-15', 'Published'),
    (4, '2024-01-08', 'Draft'),
    (5, '2024-01-18', 'Published'),
    (6, '2024-01-20', 'Draft'),
    (7, '2024-01-14', 'Published'),
    (8, '2024-01-09', 'Draft'),
    (9, '2024-01-22', 'Published'),
    (10, '2024-01-16', 'Draft');

-- Test data for Course table
INSERT INTO [dbo].[Course] ([Name], [Subject], [IntroductionText], [DifficultyLevel], [CourseID], [ModuleID])
VALUES
    ('Programming 101', 'Programming', 'Learn the basics of programming', 2, 101, 201),
    ('Web Development Basics', 'Web Development', 'Introduction to web development', 1, 102, 202),
    ('Database Fundamentals', 'Database', 'Foundations of database management', 2, 103, 203),
    ('Java Programming', 'Programming', 'Advanced Java concepts', 3, 104, 204),
    ('Python Basics', 'Programming', 'Introduction to Python programming', 1, 105, 205),
    ('Web Design Essentials', 'Web Design', 'Essential principles of web design', 2, 106, 206),
    ('SQL Mastery', 'Database', 'Advanced SQL techniques', 3, 107, 207),
    ('C# Development', 'Programming', 'C# language and application development', 2, 108, 208),
    ('JavaScript Advanced', 'Web Development', 'Advanced JavaScript concepts', 3, 109, 209),
    ('Data Science Intro', 'Data Science', 'Introduction to data science concepts', 2, 110, 210);

-- Test data for Cursist table
INSERT INTO [dbo].[Cursist] ([EmailAddress], [Name], [BirthDate], [Sex], [Address], [City], [Country])
VALUES
    ('cursist1@example.com', 'Alice Doe', '1990-05-15', 'Female', '123 Main St', 'City1', 'Country1'),
    ('cursist2@example.com', 'Bob Smith', '1985-10-20', 'Male', '456 Oak St', 'City2', 'Country2'),
    ('cursist3@example.com', 'Charlie Brown', '1993-03-25', 'Male', '789 Pine St', 'City3', 'Country3'),
    ('cursist4@example.com', 'David White', '1992-08-12', 'Male', '101 Cedar St', 'City4', 'Country4'),
    ('cursist5@example.com', 'Eva Black', '1998-01-30', 'Female', '202 Elm St', 'City5', 'Country5'),
    ('cursist6@example.com', 'Frank Grey', '1991-11-05', 'Male', '303 Maple St', 'City6', 'Country6'),
    ('cursist7@example.com', 'Grace Red', '1995-06-18', 'Female', '404 Birch St', 'City7', 'Country7'),
    ('cursist8@example.com', 'Henry Blue', '1989-09-22', 'Male', '505 Cedar St', 'City8', 'Country8'),
    ('cursist9@example.com', 'Ivy Green', '1994-12-08', 'Female', '606 Oak St', 'City9', 'Country9'),
    ('cursist10@example.com', 'Jack Yellow', '1987-04-10', 'Male', '707 Pine St', 'City10', 'Country10');

-- Test data for Enrollment table
INSERT INTO [dbo].[Enrollment] ([EnrollmentDate], [CourseName], [CursistEmailAddress])
VALUES
    ('2024-01-12', 'Programming 101', 'cursist1@example.com'),
    ('2024-01-10', 'Web Development Basics', 'cursist2@example.com'),
    ('2024-01-15', 'Database Fundamentals', 'cursist3@example.com'),
    ('2024-01-08', 'Java Programming', 'cursist4@example.com'),
    ('2024-01-18', 'Python Basics', 'cursist5@example.com'),
    ('2024-01-20', 'Web Design Essentials', 'cursist6@example.com'),
    ('2024-01-14', 'SQL Mastery', 'cursist7@example.com'),
    ('2024-01-09', 'C# Development', 'cursist8@example.com'),
    ('2024-01-22', 'JavaScript Advanced', 'cursist9@example.com'),
    ('2024-01
