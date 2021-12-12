IF NOT EXISTS(SELECT * FROM sysobjects WHERE name = 'STORES')
BEGIN

CREATE TABLE [dbo].STORES
(
    ID BIGINT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	[NAME] NVARCHAR(MAX) NOT NULL,
	LATITUDE FLOAT NOT NULL,
	LONGITUDE FLOAT NOT NULL
    )
END