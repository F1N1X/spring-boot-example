CREATE SEQUENCE customer_id_sequence;


CREATE TABLE customer(
    id BIGINT DEFAULT nextval('customer_id_sequence') PRIMARY KEY ,
    name TEXT NOT NULL ,
    email TEXT NOT NULL ,
    age INT NOT NULL
)