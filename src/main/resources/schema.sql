-- User Roles
CREATE TABLE IF NOT EXISTS Roles
(
    id   serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Parking Status
CREATE TABLE IF NOT EXISTS ParkingStatus
(
    id   serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Users
CREATE TABLE IF NOT EXISTS Users
(
    id                         serial PRIMARY KEY,
    name                       VARCHAR(100) NOT NULL,
    email                      VARCHAR(255) NOT NULL,
    roleID                     INT          NOT NULL,
    FOREIGN KEY (roleID) REFERENCES Roles (id),
    totalParkingSpots          INT          NOT NULL,
    totalParkingSpotsAvailable INT          NOT NULL
);

-- Parking Spots
CREATE TABLE IF NOT EXISTS ParkingSpots
(
    id              serial PRIMARY KEY,
    userID          INT          NOT NULL,
    FOREIGN KEY (userID) REFERENCES Users (id),
    regNumber       VARCHAR(100) NOT NULL,
    fromTime        TIMESTAMP    NOT NULL,
    toTime          TIMESTAMP    NOT NULL,
    parkingStatusId INT          NOT NULL,
    FOREIGN KEY (parkingStatusId) REFERENCES ParkingStatus (id)
);


-- Inserting Roles if not exists
INSERT INTO Roles (name)
SELECT 'ADMIN'
WHERE NOT EXISTS(SELECT 1 FROM Roles WHERE name = 'ADMIN');

INSERT INTO Roles (name)
SELECT 'USER'
WHERE NOT EXISTS(SELECT 1 FROM Roles WHERE name = 'USER');

INSERT INTO Roles (name)
SELECT 'INSPECTOR'
WHERE NOT EXISTS(SELECT 1 FROM Roles WHERE name = 'INSPECTOR');

-- Inserting Parking Status if not exists
INSERT INTO ParkingStatus (name)
SELECT 'PARKED'
WHERE NOT EXISTS(SELECT 1 FROM ParkingStatus WHERE name = 'PARKED');

INSERT INTO ParkingStatus (name)
SELECT 'UNPARKED'
WHERE NOT EXISTS(SELECT 1 FROM ParkingStatus WHERE name = 'UNPARKED');

-- Inserting test Admin if not exists
INSERT INTO Users (name, email, roleID, totalParkingSpots, totalParkingSpotsAvailable)
SELECT 'Admin', 'test@test.test', (SELECT id FROM Roles WHERE name = 'ADMIN'), 10, 10
WHERE NOT EXISTS(SELECT 1 FROM Users WHERE email = 'test@test.test');