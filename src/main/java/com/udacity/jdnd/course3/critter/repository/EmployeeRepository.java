package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select * from employee where id in" +
            "(select a.employee_id from (select employee_id from employee_days_available where days_available = :dayOfWeek)" +
            " a join\n" +
            "(select employee_id, count(*) c from employee_skills where skills in :skillList group by employee_id having c = :skillListLength)" +
            " b where a.employee_id = b.employee_id)", nativeQuery = true)
    List<Employee> findAllAvailable(@Param("dayOfWeek") Integer dayOfWeek,@Param("skillList") ArrayList<Integer> skillList, @Param("skillListLength") Integer skillListLength);
}
