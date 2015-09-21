SET SCHEMA ECONOMIA;

DROP TABLE department;
DROP TABLE product;

CREATE TABLE department(
	dept_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE),
	dept_external_id VARCHAR(5) NOT NULL UNIQUE, 
	dept_name VARCHAR(50) NOT NULL
);
--CALL SYSPROC.ADMIN_CMD( 'REORG TABLE "economia".DEPARTMENT' );
ALTER TABLE department ADD CONSTRAINT PK_dept_id PRIMARY KEY(dept_id);

--CREATE SEQUENCE SEQ_dept_id START WITH 1 INCREMENT BY 1;
-- ALTER SEQUENCE SEQ_dept_id RESTART WITH 1

CREATE TABLE product(
    product_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE),
    dept_id INT,
    product_external_id VARCHAR(5) NOT NULL UNIQUE, 
    product_name VARCHAR(50) NOT NULL,
    product_measure_unity VARCHAR(20),
    product_quantity INT,
    FOREIGN KEY(dept_id) REFERENCES department(dept_id)
);

select * from ECONOMIA.department;
select * from ECONOMIA.product;

/*CREATE TABLE price_history(
    product_id
    price
    date
    supermarket_id
);*/