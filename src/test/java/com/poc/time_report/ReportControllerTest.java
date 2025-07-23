package com.poc.time_report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.poc.time_report.controller.ReportController;
import com.poc.time_report.dto.ReportProjection;
import com.poc.time_report.repository.TimeRecordRepository;
import com.poc.time_report.utils.SecurityUtils;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

  @InjectMocks
  private ReportController reportController;

  @Mock
  private TimeRecordRepository timeRecordRepository;

  @Mock
  private Model model;

  @Mock
  private Principal principal;

  private MockedStatic<SecurityUtils> securityUtilsMock;

  @BeforeEach
  void setup() {
    securityUtilsMock = Mockito.mockStatic(SecurityUtils.class);
  }

  @AfterEach
  void tearDown() {
    securityUtilsMock.close();
  }


  @Test
  void testGetWorkHoursReport_AdminUser_ReturnsReport() {
    var start = LocalDateTime.of(2024, 7, 1, 0, 0);
    var end = LocalDateTime.of(2024, 7, 31, 23, 59);
    int page = 0;
    int size = 10;
    boolean ajax = false;

    var projections = List.of(mockReportProjection("Alice", "Project A", 40D));
    var pageResult = new PageImpl<>(projections);

    securityUtilsMock.when(SecurityUtils::isCurrentUserAdmin).thenReturn(true);
    when(timeRecordRepository.getReportData(start, end, PageRequest.of(page, size)))
        .thenReturn(pageResult);

    var view = reportController.getWorkHoursReport(start, end, page, size, ajax, principal,
        model);

    verify(model).addAttribute(eq("reports"), anyList());
    verify(model).addAttribute("totalPages", pageResult.getTotalPages());
    verify(model).addAttribute("currentPage", page);
    assertEquals("work_hours_report", view);
  }

  @Test
  void testGetWorkHoursReport_NonAdminUser_ReturnsReport() {
    var start = LocalDateTime.of(2024, 7, 1, 0, 0);
    var end = LocalDateTime.of(2024, 7, 31, 23, 59);
    int page = 1;
    int size = 5;
    boolean ajax = true;

    var projections = List.of(mockReportProjection("Bob", "Project B", 35D));
    var pageResult = new PageImpl<>(projections);

    securityUtilsMock.when(SecurityUtils::isCurrentUserAdmin).thenReturn(false);
    when(principal.getName()).thenReturn("Bob");
    when(
        timeRecordRepository.getReportDataForUser("Bob", start, end, PageRequest.of(page, size)))
        .thenReturn(pageResult);

    String view = reportController.getWorkHoursReport(start, end, page, size, ajax, principal,
        model);

    verify(model).addAttribute(eq("reports"), anyList());
    verify(model).addAttribute("totalPages", pageResult.getTotalPages());
    verify(model).addAttribute("currentPage", page);
    assertEquals("report_table", view);
  }

  @Test
  void testGetWorkHoursReport_WithNullDates_DefaultsToLastMonth() {
    securityUtilsMock.when(SecurityUtils::isCurrentUserAdmin).thenReturn(true);
    when(timeRecordRepository.getReportData(any(), any(), any()))
        .thenReturn(new PageImpl<>(Collections.emptyList()));

    var view = reportController.getWorkHoursReport(null, null, 0, 10, false, principal, model);

    verify(model).addAttribute(eq("reports"), anyList());
    assertEquals("work_hours_report", view);
  }

  private ReportProjection mockReportProjection(String employeeName, String projectName,
      Double totalHours) {
    ReportProjection mock = mock(ReportProjection.class);
    when(mock.getEmployeeName()).thenReturn(employeeName);
    when(mock.getProjectName()).thenReturn(projectName);
    when(mock.getTotalHours()).thenReturn(totalHours);
    return mock;
  }
}
