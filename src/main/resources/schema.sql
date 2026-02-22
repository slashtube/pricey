CREATE TABLE IF NOT EXISTS products (
    EAN VARCHAR(16),
    Description VARCHAR(256) NOT NULL,

    PRIMARY KEY(EAN)
);

CREATE TABLE IF NOT EXISTS catalogs (
    file VARCHAR(256),

    PRIMARY KEY(file)
);

CREATE TABLE IF NOT EXISTS entries (
    EAN VARCHAR(16),
    file VARCHAR(256),
    price DOUBLE NOT NULL,

    FOREIGN KEY (EAN) REFERENCES products(EAN),
    FOREIGN KEY (file) REFERENCES catalogs(file)
);