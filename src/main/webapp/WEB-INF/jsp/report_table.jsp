<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Report Table -->
<c:if test="${not empty reports}">
  <div class="table-responsive">
    <table class="table table-bordered table-striped align-middle">
      <thead class="table-dark">
      <tr>
        <th>Employee Name</th>
        <th>Project Name</th>
        <th>Total Hours</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="report" items="${reports}">
        <tr>
          <td>${report.employeeName}</td>
          <td>${report.projectName}</td>
          <td>${report.totalHours}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</c:if>

<c:if test="${empty reports}">
  <div class="alert alert-warning" role="alert">
    No report data available for the selected date range.
  </div>
</c:if>

<!-- AJAX Pagination -->
<c:if test="${totalPages > 1}">
  <nav aria-label="Page navigation" class="mt-4">
    <ul class="pagination justify-content-center">
      <c:forEach begin="0" end="${totalPages - 1}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
          <a class="page-link pagination-link"
             href="/report?startDate=${startDate}&endDate=${endDate}&page=${i}&size=10&ajax=true">
              ${i + 1}
          </a>
        </li>
      </c:forEach>
    </ul>
  </nav>
</c:if>
