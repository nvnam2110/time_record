package com.poc.time_report.controller;

import static com.poc.time_report.utils.SecurityUtils.isCurrentUserAdmin;

import com.poc.time_report.dto.ReportDTO;
import com.poc.time_report.dto.ReportProjection;
import com.poc.time_report.repository.TimeRecordRepository;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReportController {

  private final TimeRecordRepository timeRecordRepository;

  @GetMapping("/report")
  public String getWorkHoursReport(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "false") boolean ajax,
      Principal principal,
      Model model) {

    if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
      startDate = LocalDateTime.now().minusMonths(1);
      endDate = LocalDateTime.now();
    }

    var pageable = PageRequest.of(page, size);

    Page<ReportProjection> reportPage;

    if (isCurrentUserAdmin()) {
      reportPage = timeRecordRepository.getReportData(startDate, endDate, pageable);
    } else {
      reportPage = timeRecordRepository.getReportDataForUser(principal.getName(), startDate,
          endDate, pageable);
    }
    var report = reportPage.stream()
        .map(projection -> new ReportDTO(
            projection.getEmployeeName(),
            projection.getProjectName(),
            projection.getTotalHours()
        )).toList();

    model.addAttribute("reports", report);
    model.addAttribute("totalPages", reportPage.getTotalPages());
    model.addAttribute("currentPage", page);
    return ajax ? "report_table" : "work_hours_report";
  }
}
