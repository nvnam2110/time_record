# Work Hours Reporting Page Documentation

## How to run Spring Boot Application
1. **Prerequisites**:
   - Ensure you have Java Development Kit (JDK) installed (version 11 or higher).
   - Install Gradle for dependency management.
   - Ensure you have a Docker PostgresSQL database set up and running.
2. **Clone the Repository**:
   ```bash
   git clone https://github.com/nvnam2110/time_record.git
   ```
3. **Navigate to the Project Directory**:
   - Open a terminal or command prompt and navigate to the cloned project directory.
   - Access the project directory:
   ```bash 
   cd time_report\src\main\docker\docker-compose.yml
   docker-compose up -d
   ```
4. **Build the Application**:
    ```bash
    ./gradlew build
    ```
5. **Run the Application**:
    ```bash
    ./gradlew bootRun 
    ```
6. **Access the Application**:
7. Open a web browser and navigate to `http://localhost:8080/report` to access the reporting page.
8. Access the application using the credentials:
   - Username: `admin`
   - Password: `admin`

## How to Use the Reporting Page

1. **Accessing the Page**:  
   Navigate to the reporting page in your browser. The URL is typically `/report`.

2. **Filtering by Date Range**:
   - Use the **Start Date** and **End Date** fields to specify the date range for the report.
   - Click the **Generate** button to fetch the report for the selected date range.

3. **Viewing the Report**:
   - The report is displayed in a table format, showing work hours grouped by employee and project.
   - Pagination is available for large datasets. Click on the pagination links to navigate between pages.

4. **Dynamic Updates**:
   - The page uses AJAX to dynamically update the report table without reloading the entire page.
   - If you modify the date range or click on a pagination link, the table will refresh automatically.

## Assumptions and Design Decisions

1. **AJAX for Dynamic Updates**:  
   The reporting page uses AJAX to improve user experience by dynamically updating the report table without requiring a full page reload.

2. **Date Range Filtering**:
   - The date range filter assumes that users will input valid `datetime-local` values.
   - Default values for `startDate` and `endDate` are pre-populated if provided by the server.

3. **Backend Integration**:
   - The `/report` endpoint is responsible for fetching the report data. It supports both full-page and AJAX requests (via the `ajax=true` query parameter).

4. **Pagination**:
   - Pagination is implemented to handle large datasets efficiently.
   - The backend is expected to return paginated results when requested.

5. **Security**:
   - Input validation and sanitization are performed on the server side to prevent SQL injection and other vulnerabilities.
   - The page uses HTTPS for secure communication.

6. **Styling and Responsiveness**:
   - The page uses Bootstrap 5 for styling and ensures responsiveness across different devices.
   - The design prioritizes simplicity and usability.

## Security notes:

1. **UserDetailsService**: A custom `UserDetailsService` is configured to load user details (e.g., username, password, roles) from the database or another source.

2. **Roles and Authorities**: Roles are assigned to users, and these roles are mapped to specific authorities that define access permissions.

3. **SecurityFilterChain**: The `SecurityFilterChain` bean is configured to define access rules for different endpoints. For example:
   - Public endpoints (e.g., login) are accessible to everyone.
   - Restricted endpoints (e.g., `/report`) require specific roles (e.g., `ROLE_ADMIN` or `ROLE_USER`).

5. **Authentication and Authorization**: Spring Security automatically checks the user's roles during authentication and ensures they have the required permissions to access specific resources.
