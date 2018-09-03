package com.java8.basics;

import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class AppTest {

    List<Employee> employeeList = new ArrayList<>();

    @Before
    public void init() {
        employeeList.add(new Employee(1, "Jitu", "Saini", 27));
        employeeList.add(new Employee(2, "Neeraj", "Jain", 26));
        employeeList.add(new Employee(3, "Sagar", "Batra", 29));
        employeeList.add(new Employee(4, "Ashraff", "Syed", 32));
        employeeList.add(new Employee(5, "Vibhum", "Bakshi", 25));
    }

    @Test
    public void whenMapIdToEmployees_thenGetEmployeeStream() {
        List<Integer> empIds = Arrays.asList(1, 2, 3, 4345); // Last Id is junk

        List<Employee> filteredEmployees = empIds.stream()
                .map(empId -> findById(empId))
                .filter(employee -> employee != null)
                .collect(Collectors.toList());

        assertThat(filteredEmployees.size()).isEqualTo(empIds.size() - 1);
    }

    public Employee findById(int empId) {
        return employeeList.stream().filter(employee -> employee.empId == empId).findFirst().orElse(null);
    }
}
