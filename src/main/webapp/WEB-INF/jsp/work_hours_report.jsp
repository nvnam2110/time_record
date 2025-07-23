<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Work Hours Report</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          integrity="sha384-..." crossorigin="anonymous">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Work Hours Report</h2>

    <!-- Date Range Filter Form -->
    <form class="row g-3 mb-4" id="filter-form">
        <div class="col-md-5">
            <label for="startDate" class="form-label">Start Date</label>
            <input type="datetime-local" class="form-control" id="startDate" name="startDate"
                   value="${startDate}">
        </div>
        <div class="col-md-5">
            <label for="endDate" class="form-label">End Date</label>
            <input type="datetime-local" class="form-control" id="endDate" name="endDate"
                   value="${endDate}">
        </div>
        <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">Generate</button>
        </div>
    </form>

    <!-- Report Content (Table + Pagination) -->
    <div id="report-container">
        <jsp:include page="report_table.jsp"/>
    </div>
</div>

<!-- Bootstrap JS & optional dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-..." crossorigin="anonymous"></script>

<!-- AJAX Script -->
<script>
  function loadReport(url) {
    fetch(url)
    .then(response => response.text())
    .then(html => {
      document.getElementById("report-container").innerHTML = html;
    })
    .catch(err => console.error("AJAX load error:", err));
  }

  // AJAX form submission
  document.getElementById("filter-form").addEventListener("submit", function (e) {
    e.preventDefault();
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const url = `/report?startDate=${startDate}&endDate=${endDate}&ajax=true`;
    loadReport(url);
  });

  // Delegate pagination clicks
  document.addEventListener("click", function (e) {
    if (e.target.classList.contains("pagination-link")) {
      e.preventDefault();
      loadReport(e.target.href);
    }
  });
</script>
</body>
</html>
