# E-commerce SDET Automation Framework

## Overview
This Java/Maven project demonstrates UI automation for the SauceDemo storefront and API tests against JSONPlaceholder. The UI suite uses Selenium WebDriver, TestNG, and the Page Object Model (POM); API tests use REST Assured.

## Tech Stack
- Java 17
- Maven
- Selenium 4.49.0
- TestNG 7.11.0
- REST Assured 5.5.0
- Jackson Databind 2.17.2
- Chrome WebDriver (headless execution)

## Architecture
- UI page objects are in `automation-project/src/test/java/com/ecommerce/framework/pages`; UI tests and shared browser setup are in `.../tests`.
- API tests and their shared REST Assured setup are in `.../api/tests` and `.../api/api`.
- `ConfigLoader` reads the shared test settings from `automation-project/src/test/resources/config.properties`.
- `ScreenshotListener` captures screenshots when Selenium tests fail.
- `automation-project/testng.xml` defines the TestNG suite used by Maven Surefire.

The UI tests cover Chrome only. Cross-browser and parallel execution are not configured.

## UI Coverage
- Valid and invalid login
- Product listing and visibility
- Add/remove product from the cart
- Cart contents and removal
- Successful checkout flow

## API Coverage
- `GET /posts`, `GET /posts/{id}`, and `GET /posts?userId={id}`
- `POST /posts`, `PUT /posts/{id}`, `PATCH /posts/{id}`, and `DELETE /posts/{id}`
- Negative/read-filter checks for an unknown post ID and comments for a missing post ID

## Project Structure
```text
.
├── .github/
│   └── workflows/maven-tests.yml
├── automation-project/
│   ├── pom.xml
│   ├── testng.xml
│   └── src/test/
│       ├── java/com/ecommerce/framework/
│       │   ├── api/api/ and api/tests/
│       │   ├── listeners/
│       │   ├── pages/
│       │   ├── tests/
│       │   └── utils/
│       └── resources/config.properties
├── .gitignore
├── README.md
```

## Configuration
The test endpoints are centralized in `automation-project/src/test/resources/config.properties`:

```properties
baseUrl=https://www.saucedemo.com/
apiBaseUrl=https://jsonplaceholder.typicode.com
```

## Run Tests

Run the full TestNG suite from the Maven project directory:
```bash
cd automation-project
mvn clean test
```

Run only the cart test:
```bash
mvn -Dtest=CartTest test
```

## Reporting
Maven Surefire and TestNG produce HTML and XML reports under:

```text
automation-project/target/surefire-reports/
```

## Screenshots
The TestNG listener saves screenshots for failed Selenium tests under:

```text
automation-project/target/screenshots/
```

Successful runs normally have no failure screenshots. GitHub Actions still attempts to upload both report and screenshot paths after the test step (`if: always()`); missing paths are warnings rather than workflow failures. The artifact is named `test-reports`.

## GitHub Actions
The workflow at `.github/workflows/maven-tests.yml` runs on pushes and pull requests targeting `main`. It uses Java 17 with Temurin and Maven caching, runs `mvn clean test`, and uploads Surefire reports and any failure screenshots as an artifact.

## JSONPlaceholder Limitation
JSONPlaceholder is a mock API. The POST, PUT, PATCH, and DELETE tests validate the returned status and response data, but those mutations are simulated and are not persisted as changes to the API's stored data.
