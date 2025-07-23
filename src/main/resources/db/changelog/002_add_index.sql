CREATE INDEX idx_time_record_time_from ON time_record(time_from);
CREATE INDEX idx_time_record_employee_id ON time_record(employee_id);
CREATE INDEX idx_time_record_composite ON time_record(time_from, employee_id, project_id);