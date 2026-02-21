CREATE TABLE Products {
    EAN VARCHAR(16),
    Description VARCHAR(256) NOT NULL,

    PRIMARY KEY(EAN)
}

CREATE TABLE Catalogs {
    Name VARCHAR(256) NOT NULL,
    file VARCHAR(256),

    PRIMARY KEY(file)
}

CREATE TABLE Entries {
    EAN VARCHAR(16),
    file VARCHAR(256),

    FOREIGN KEY (EAN) REFERENCES Products(EAN)
    FOREIGN KEY (file) REFERENCES Catalogs(file)
}