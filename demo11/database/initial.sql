CREATE TABLE leads (
    id int PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    middleName VARCHAR(255),
    lastName VARCHAR(255) NOT NULL,
    mobileNumber VARCHAR(255)  NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dob VARCHAR(100) NOT NULL,
    email VARCHAR(255)
);

