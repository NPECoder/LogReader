DROP TABLE EVENT IF EXISTS;
CREATE TABLE EVENT (
    ID varchar(255) primary key,
    Duration Integer not null,
    Type varchar(255) null,
    Host varchar(50) null,
    AlertFlag BOOLEAN DEFAULT FALSE not null
);