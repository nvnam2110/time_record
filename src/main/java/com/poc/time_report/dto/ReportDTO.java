package com.poc.time_report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

  private String employeeName;
  private String projectName;
  private Double totalHours;
}
