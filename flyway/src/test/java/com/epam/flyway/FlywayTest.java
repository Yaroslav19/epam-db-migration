package com.epam.flyway;

import com.epam.domain.Company;
import com.epam.domain.Employee;
import com.epam.domain.User;
import com.epam.repository.CompanyRepository;
import com.epam.repository.EmployeeRepository;
import com.epam.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class FlywayTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void databaseHasBeenInitialized() {
        jdbcTemplate.execute(
                "insert into test_user (username, first_name, last_name) values('ivanspresov', 'Ivan', 'Spresov')");

        final List<User> users = jdbcTemplate
                .query("SELECT username, first_name, last_name FROM test_user", (rs, rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        null
                ));
        for (User user : users) {
            System.out.println(user);
        }

        assertThat(users).isNotEmpty();
    }

    @Test
    void companyTableExists() {
        jdbcTemplate.execute("insert into company (name, description) values('EPAM', '...')");

        List<Company> companies = jdbcTemplate
                .query("SELECT id, name, description FROM company", (rs, rowNum) -> new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        null
                ));

        assertThat(companies).isNotEmpty();
    }

    @Test
    void employeeCompanyAssociation() {
        Company company = companyRepository.save(
                Company.builder().name("PSG").description("...").build());

        employeeRepository.save(
                Employee.builder().fio("Leo Messi").company(company).build());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty();
    }

    @Test
    void userEmployeeAltering() {
        Employee employee = employeeRepository.save(
                Employee.builder().fio("Leo Messi").build());

        userRepository.save(new User("username", "user", "user", employee));

        User user = userRepository.findByEmployee_Fio("Leo Messi");
        assertNotNull(user);
        assertNotNull(user.getEmployee());
    }
}