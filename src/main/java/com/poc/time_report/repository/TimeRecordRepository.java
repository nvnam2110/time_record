package com.poc.time_report.repository;

import com.poc.time_report.dto.ReportDTO;
import com.poc.time_report.dto.ReportProjection;
import com.poc.time_report.entity.TimeRecord;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {

  @Query(
      value = """
        SELECT e.name AS employeeName, 
               p.name AS projectName, 
               SUM(EXTRACT(EPOCH FROM (tr.time_to - tr.time_from)) / 3600) AS totalHours
        FROM time_record tr
        JOIN employee e ON e.id = tr.employee_id
        JOIN project p ON p.id = tr.project_id
        WHERE tr.time_from BETWEEN :startDate AND :endDate
        GROUP BY e.name, p.name
        ORDER BY e.name, p.name
        """,
      countQuery = """
        SELECT COUNT(*) 
        FROM (
            SELECT 1
            FROM time_record tr
            JOIN employee e ON e.id = tr.employee_id
            JOIN project p ON p.id = tr.project_id
            WHERE tr.time_from BETWEEN :startDate AND :endDate
            GROUP BY e.name, p.name
        ) AS count_table
        """,
      nativeQuery = true
  )
  Page<ReportProjection> getReportData(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      Pageable pageable);

  @Query(value = """
    SELECT e.name AS employeeName,
           p.name AS projectName,
           SUM(EXTRACT(EPOCH FROM (tr.time_to - tr.time_from)) / 3600) AS totalHours
    FROM time_record tr
    JOIN employee e ON e.id = tr.employee_id
    JOIN project p ON p.id = tr.project_id
    WHERE e.username = :username AND tr.time_from BETWEEN :startDate AND :endDate
    GROUP BY e.name, p.name
    ORDER BY e.name, p.name
    """,
      countQuery = """
    SELECT COUNT(*) FROM (
        SELECT 1
        FROM time_record tr
        JOIN employee e ON e.id = tr.employee_id
        JOIN project p ON p.id = tr.project_id
        WHERE e.username = :username AND tr.time_from BETWEEN :startDate AND :endDate
        GROUP BY e.name, p.name
    ) AS count_table
    """,
      nativeQuery = true
  )
  Page<ReportProjection> getReportDataForUser(
      @Param("username") String username,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      Pageable pageable);

}
