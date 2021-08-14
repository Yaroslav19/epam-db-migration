CREATE TABLE employee(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fio VARCHAR(255) NOT NULL UNIQUE,
    company_id INT,
    CONSTRAINT fk_employee_company foreign key (company_id) references company(id)
);