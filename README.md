1. Part 1: Database Query Optimization
   Re-write the SQL command using inefficient constructs (such as multiple subqueries) so that it is nonperformant. For example:
    ```sql
    SELECT
    t.employee_id,
    (SELECT name FROM employee e WHERE e.id = t.employee_id) AS employee_name,
    (SELECT name FROM project p WHERE p.id = t.project_id) AS project_name,
    SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600) AS total_hours
    FROM time_record t
    WHERE t.time_from >= NOW() - INTERVAL '1 month'
    GROUP BY
    t.employee_id,
    (SELECT name FROM employee e WHERE e.id = t.employee_id),
    (SELECT name FROM project p WHERE p.id = t.project_id)
    ORDER BY
    (SELECT name FROM employee e WHERE e.id = t.employee_id),
    (SELECT name FROM project p WHERE p.id = t.project_id);
    ```
Analyze the Query Performance:
- Each (SELECT name FROM ...) is executed for every row, potentially repeating thousands of times
Sub query will make the query slow, especially with large datasets
- Missing some index in where condition which could speed up the filtering
- The GROUP BY clause is also inefficient as it uses subqueries instead of direct joins
- The ORDER BY clause is also using subqueries, which can lead to additional performance issues

Optimize the Query:
- Suggest and implement appropriate indexing strategies.
1. time_record.time_from (used in WHERE)
   ```sql
   CREATE INDEX idx_time_record_time_from ON time_record(time_from);
   ```
2. time_record.employee_id and project_id (used in JOINs)
   ```sql 
   CREATE INDEX idx_time_record_employee_id ON time_record(employee_id);
   ```
   ```sql 
   CREATE INDEX idx_time_record_project_id ON time_record(project_id);
3. Composite index for (time_from, employee_id, project_id)
   ```sql 
   CREATE INDEX idx_time_record_composite ON time_record(time_from, employee_id, project_id);
   ```
- Propose improvements such as materialized views, partitioning, or caching where applicable
1. Partition
    - If the time_record table is large, consider partitioning it by month or year based on the 
time_from column to improve query performance.
    - Example: 
   ```sql
   CREATE TABLE time_record_y2023 PARTITION OF time_record FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');
   CREATE TABLE time_record_2024_08 PARTITION OF time_record FOR VALUES FROM ('2024-08-01') TO ('2024-09-01');
   ```


