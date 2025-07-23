CREATE MATERIALIZED VIEW monthly_work_hours_report AS
SELECT
    e.id AS employee_id,
    e.name AS employee_name,
    p.id AS project_id,
    p.name AS project_name,
    DATE_TRUNC('month', tr.time_from) AS report_month,
    SUM(EXTRACT(EPOCH FROM (tr.time_to - tr.time_from)) / 3600) AS total_hours
FROM time_record tr
         JOIN employee e ON tr.employee_id = e.id
         JOIN project p ON tr.project_id = p.id
GROUP BY e.id, e.name, p.id, p.name, DATE_TRUNC('month', tr.time_from);

REFRESH MATERIALIZED VIEW monthly_work_hours_report;
