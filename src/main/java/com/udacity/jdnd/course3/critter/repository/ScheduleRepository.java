package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>  {
    @Query("select s from Schedule s where :pet member of s.pets")
    List<Schedule> findAllByPet(@Param("pet") Pet pet);

    @Query("select s from Schedule s where :employee member of s.employees")
    List<Schedule> findAllByEmployee(@Param("employee") Employee employee);

    @Query(value = "select * from schedule where id in " +
            "(select distinct schedule_id from schedule_pets where pets_id in " +
            "(select distinct id from pet where owner_id = :userId))", nativeQuery = true)
    List<Schedule> findAllByUserId(@Param("userId") Long userId);
}
