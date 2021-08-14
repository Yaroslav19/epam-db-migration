ALTER TABLE test_user ADD COLUMN employee_id INT;
ALTER TABLE test_user ADD CONSTRAINT fk_user_employee FOREIGN KEY (employee_id) REFERENCES employee(id);