USE [master]
GO
/****** Object:  Database [CodeCademy]    Script Date: 26-2-2024 11:57:29 ******/
CREATE DATABASE [CodeCademy]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CodeCademy', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\CodeCademy.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CodeCademy_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\CodeCademy_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [CodeCademy] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CodeCademy].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CodeCademy] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CodeCademy] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CodeCademy] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CodeCademy] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CodeCademy] SET ARITHABORT OFF 
GO
ALTER DATABASE [CodeCademy] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [CodeCademy] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CodeCademy] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CodeCademy] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CodeCademy] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CodeCademy] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CodeCademy] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CodeCademy] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CodeCademy] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CodeCademy] SET  DISABLE_BROKER 
GO
ALTER DATABASE [CodeCademy] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CodeCademy] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CodeCademy] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CodeCademy] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CodeCademy] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CodeCademy] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CodeCademy] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CodeCademy] SET RECOVERY FULL 
GO
ALTER DATABASE [CodeCademy] SET  MULTI_USER 
GO
ALTER DATABASE [CodeCademy] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CodeCademy] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CodeCademy] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CodeCademy] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [CodeCademy] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [CodeCademy] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'CodeCademy', N'ON'
GO
ALTER DATABASE [CodeCademy] SET QUERY_STORE = ON
GO
ALTER DATABASE [CodeCademy] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [CodeCademy]
GO
/****** Object:  User [admin]    Script Date: 26-2-2024 11:57:29 ******/
CREATE USER [admin] FOR LOGIN [admin] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [admin]
GO
ALTER ROLE [db_datareader] ADD MEMBER [admin]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [admin]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateID] [int] IDENTITY(1,1) NOT NULL,
	[CourseName] [varchar](50) NOT NULL,
	[CursistEmailAddress] [nvarchar](50) NOT NULL,
	[EnrollmentDate] [date] NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[CertificateID] ASC,
	[CourseName] ASC,
	[CursistEmailAddress] ASC,
	[EnrollmentDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContentItem]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContentItem](
	[ContentItemID] [int] IDENTITY(1,1) NOT NULL,
	[PublicationDate] [date] NULL,
	[Status] [varchar](50) NULL,
 CONSTRAINT [PK_ContentItem] PRIMARY KEY CLUSTERED 
(
	[ContentItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[Name] [varchar](50) NOT NULL,
	[Subject] [varchar](50) NULL,
	[IntroductionText] [varchar](50) NULL,
	[DifficultyLevel] [varchar](50) NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cursist]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cursist](
	[EmailAddress] [nvarchar](50) NOT NULL,
	[Name] [varchar](50) NULL,
	[BirthDate] [date] NULL,
	[Sex] [varchar](50) NULL,
	[Address] [varchar](50) NULL,
	[City] [varchar](50) NULL,
	[Country] [varchar](50) NULL,
	[zipCode] [nvarchar](7) NULL,
 CONSTRAINT [PK_Cursist_1] PRIMARY KEY CLUSTERED 
(
	[EmailAddress] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[EnrollmentDate] [date] NOT NULL,
	[CourseName] [varchar](50) NOT NULL,
	[CursistEmailAddress] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Enrollment_1] PRIMARY KEY CLUSTERED 
(
	[EnrollmentDate] ASC,
	[CourseName] ASC,
	[CursistEmailAddress] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[Title] [varchar](50) NOT NULL,
	[Version] [varchar](50) NOT NULL,
	[ContactPersonName] [varchar](50) NULL,
	[ContactPersonEmail] [varchar](50) NULL,
	[FollowNumber] [int] NULL,
	[ContentItemID] [int] NOT NULL,
	[CourseName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[Title] ASC,
	[Version] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WatchedContent]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WatchedContent](
	[ContentItemID] [int] NOT NULL,
	[CursistEmailAddress] [nvarchar](50) NOT NULL,
	[PercentageWatched] [int] NULL,
 CONSTRAINT [PK_WatchedContent] PRIMARY KEY CLUSTERED 
(
	[ContentItemID] ASC,
	[CursistEmailAddress] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 26-2-2024 11:57:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[Title] [varchar](50) NOT NULL,
	[Duration] [int] NOT NULL,
	[PublicationDate] [date] NULL,
	[URL] [varchar](50) NULL,
	[NameSpeaker] [varchar](50) NULL,
	[OrganisationSpeaker] [varchar](50) NULL,
	[ContentItemID] [int] NOT NULL,
	[Description] [varchar](50) NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[Title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK_Certificate_Enrollment] FOREIGN KEY([EnrollmentDate], [CourseName], [CursistEmailAddress])
REFERENCES [dbo].[Enrollment] ([EnrollmentDate], [CourseName], [CursistEmailAddress])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Certificate] CHECK CONSTRAINT [FK_Certificate_Enrollment]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Cursist] FOREIGN KEY([CursistEmailAddress])
REFERENCES [dbo].[Cursist] ([EmailAddress])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Cursist]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_ContentItem] FOREIGN KEY([ContentItemID])
REFERENCES [dbo].[ContentItem] ([ContentItemID])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_ContentItem]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Name])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Course]
GO
ALTER TABLE [dbo].[WatchedContent]  WITH CHECK ADD  CONSTRAINT [FK_WatchedContent_ContentItem] FOREIGN KEY([ContentItemID])
REFERENCES [dbo].[ContentItem] ([ContentItemID])
GO
ALTER TABLE [dbo].[WatchedContent] CHECK CONSTRAINT [FK_WatchedContent_ContentItem]
GO
ALTER TABLE [dbo].[WatchedContent]  WITH CHECK ADD  CONSTRAINT [FK_WatchedContent_Cursist] FOREIGN KEY([CursistEmailAddress])
REFERENCES [dbo].[Cursist] ([EmailAddress])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[WatchedContent] CHECK CONSTRAINT [FK_WatchedContent_Cursist]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_ContentItem] FOREIGN KEY([ContentItemID])
REFERENCES [dbo].[ContentItem] ([ContentItemID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_ContentItem]
GO
USE [master]
GO
ALTER DATABASE [CodeCademy] SET  READ_WRITE 
GO
