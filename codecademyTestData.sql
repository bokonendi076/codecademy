-- Course table
INSERT INTO [CodeCademy].[dbo].[Course] ([Name], [Subject], [IntroductionText], [DifficultyLevel]) 
VALUES 
    (N'Mathematics 101', N'Mathematics', N'Welcome to Mathematics 101', N'Beginner'),
    (N'Physics 202', N'Physics', N'Welcome to Physics 202', N'Intermediate'),
    (N'History 101', N'History', N'Welcome to History 101', N'Intermediate'),
    (N'Computer Science 101', N'Computer Science', N'Welcome to Computer Science 101', N'Intermediate'),
    (N'Chemistry 101', N'Chemistry', N'Welcome to Chemistry 101', N'Beginner');

-- ContentItem table
INSERT INTO [CodeCademy].[dbo].[ContentItem] ([PublicationDate], [Status]) 
VALUES 
    ('2023-01-01', N'Published'),
    ('2023-02-02', N'Draft'),
    ('2023-03-03', N'Published'),
    ('2023-04-04', N'Draft'),
    ('2023-05-05', N'Published');

-- Cursist table
INSERT INTO [CodeCademy].[dbo].[Cursist] ([EmailAddress], [Name], [BirthDate], [Sex], [Address], [City], [Country], [zipCode]) 
VALUES 
    (N'cursist1@email.com', N'John Doe', '1990-01-01', N'Male', N'123 Main St', N'New York', N'USA', NULL),
    (N'cursist2@email.com', N'Jane Smith', '1995-02-15', N'Female', N'456 Elm St', N'Los Angeles', N'USA', NULL),
    (N'cursist3@email.com', N'Alice Johnson', '1988-05-20', N'Female', N'789 Oak St', N'Chicago', N'USA', N'1234LK'),
    (N'cursist4@email.com', N'Bob Brown', '1992-07-10', N'Male', N'101 Pine St', N'Houston', N'USA', NULL),
    (N'cursist5@email.com', N'Emma Davis', '1998-11-30', N'Female', N'202 Cedar St', N'Miami', N'USA', NULL);

-- Enrollment table
INSERT INTO [CodeCademy].[dbo].[Enrollment] ([EnrollmentDate], [CourseName], [CursistEmailAddress]) 
VALUES 
    ('2023-01-01', N'Mathematics 101', N'cursist1@email.com'),
    ('2023-02-02', N'Physics 202', N'cursist2@email.com'),
    ('2023-03-03', N'History 101', N'cursist3@email.com'),
    ('2023-04-04', N'Computer Science 101', N'cursist4@email.com'),
    ('2023-05-05', N'Chemistry 101', N'cursist5@email.com');

-- Module table
INSERT INTO [CodeCademy].[dbo].[Module] ([Title], [Version], [ContactPersonName], [ContactPersonEmail], [FollowNumber], [ContentItemID], [CourseName]) 
VALUES 
    (N'Module 1', N'v1', N'John Doe', N'john@example.com', 100, 1, N'Mathematics 101'),
    (N'Module 2', N'v1', N'Jane Smith', N'jane@example.com', 150, 2, N'Physics 202'),
    (N'Module 3', N'v1', N'Alice Johnson', N'alice@example.com', 120, 3, N'History 101'),
    (N'Module 4', N'v1', N'Bob Brown', N'bob@example.com', 200, 4, N'Computer Science 101'),
    (N'Module 5', N'v1', N'Emma Davis', N'emma@example.com', 180, 5, N'Chemistry 101');

-- Certificate table
INSERT INTO [CodeCademy].[dbo].[Certificate] ([CourseName], [CursistEmailAddress], [EnrollmentDate]) 
VALUES 
    (N'Mathematics 101', N'cursist1@email.com', '2023-01-01'),
    (N'Physics 202', N'cursist2@email.com', '2023-02-02'),
    (N'History 101', N'cursist3@email.com', '2023-03-03'),
    (N'Computer Science 101', N'cursist4@email.com', '2023-04-04'),
    (N'Chemistry 101', N'cursist5@email.com', '2023-05-05');

-- WatchedContent table
INSERT INTO [CodeCademy].[dbo].[WatchedContent] ([ContentItemID], [CursistEmailAddress], [PercentageWatched]) 
VALUES 
    (1, N'cursist1@email.com', 50),
    (1, N'cursist2@email.com', 20),
    (1, N'cursist3@email.com', 50),
    (1, N'cursist5@email.com', 10),
    (2, N'cursist1@email.com', 30),
    (2, N'cursist2@email.com', 75),
    (2, N'cursist5@email.com', 40),
    (3, N'cursist1@email.com', 90),
    (3, N'cursist3@email.com', 60),
    (4, N'cursist4@email.com', 80);

-- Webcast table
INSERT INTO [dbo].[Webcast] ([Title], [Duration], [PublicationDate], [URL], [NameSpeaker], [OrganisationSpeaker], [ContentItemID], [Description]) 
VALUES 
    (N'Introduction to Web Development', 60, CAST(N'2023-01-01' AS Date), N'http://example.com/webcast1', N'John Doe', N'WebTech Inc.', 1, N'Introduction to Web Development'),
    (N'Advanced Machine Learning Techniques', 90, CAST(N'2023-02-02' AS Date), N'http://example.com/webcast2', N'Jane Smith', N'AI Labs', 2, N'Advanced Machine Learning Techniques'),
    (N'Effective Communication in Business', 45, CAST(N'2023-03-03' AS Date), N'http://example.com/webcast3', N'Alice Johnson', N'BizCom Consulting', 3, N'Effective Communication in Business'),
    (N'Step into webdesign', 55, CAST(N'2023-03-01' AS Date), N'http://example.com/webcast4', N'John Doe', N'DesignsBusiness', 4, N'Step into webdesign');

