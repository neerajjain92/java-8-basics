package com.java8.basics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.java8.basics.Employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;

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

        Employee employee = empIds.stream().map(empId -> findById(empId))
                .filter(employee1 -> employee1 != null)
                .filter(employee1 -> employee1.age > 21)
                .findFirst().orElse(null);
        assertThat(employee).isNotNull();

        Employee[] employeeArr = empIds.stream().map(empId -> findById(empId)).filter(empObj -> empObj != null).toArray(Employee[]::new);
        assertThat(employeeArr.length).isEqualTo(3);
    }

    @Test
    public void whenStreamCount_thenGetElementCount() {
        // Example of Stream Pipeline, where filter is an intermediate operation
        // and count is the terminal operation after which stream can no longer be consumed
        Long employeesGreaterThan26Years = employeeList.stream().filter(employee -> employee.age > 26).count();
        assertThat(employeesGreaterThan26Years).isEqualTo(3);
    }

    public Employee findById(int empId) {
        return employeeList.stream().filter(employee -> employee.empId == empId).findFirst().orElse(null);
    }
}
