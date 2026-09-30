# E-commerce SDET Automation Framework

## Overview
This project is a Java-based Selenium and REST Assured automation framework for validating a typical e-commerce checkout flow and API interactions. It follows a clean Page Object Model for UI automation and keeps the API testing simple and interview-friendly.

## Tech Stack
- Java 17
- Maven
- Selenium 4.49.0
- TestNG 7.11.0
- REST Assured 5.5.0
- Jackson Databind 2.17.2
- Chrome browser (headless execution in CI)

## Architecture
- UI tests use page objects under `src/test/java/com/ecommerce/framework/pages`
- Test classes live under `src/test/java/com/ecommerce/framework/tests`
- API tests live under `src/test/java/com/ecommerce/framework/api/tests`
- Shared setup and configuration live in `BaseTest`, `BaseApiTest`, and `ConfigLoader`
- TestNG listener captures failure screenshots automatically

## UI Coverage
- Login flows
- Product listing checks
- Add/remove cart actions
- Cart verification
- Checkout happy path

## API Coverage
- GET /posts
- GET /posts/{id}
- GET /posts?userId={id}
- POST /posts
- PUT /posts/{id}
- PATCH /posts/{id}
- DELETE /posts/{id}
- Negative validation for invalid IDs and empty results

## Project Structure
```text
.
├── .github/workflows/maven-tests.yml
├── automation-project/
│   ├── pom.xml
│   ├── testng.xml
│   └── src/
│       ├── test/java/com/ecommerce/framework/
│       │   ├── api/
│       │   ├── listeners/
│       │   ├── pages/
│       │   ├── tests/
│       │   └── utils/
│       └── test/resources/config.properties
├── .gitignore
├── README.md
└── .vscode/
```

## How to Run
From the project root:

```bash
cd automation-project
mvn clean test
```

The project is already wired to the TestNG suite in `automation-project/testng.xml`, so the standard entry point remains:

```bash
cd automation-project
mvn clean test
```

## Reporting
TestNG generates the default HTML XML reports under:

```text
automation-project/target/surefire-reports/
```

## Screenshots
When a Selenium test fails, the listener stores screenshots under:

```text
automation-project/target/screenshots/
```

## CI/CD
GitHub Actions runs the Maven suite on push and pull request and uploads the generated test reports and screenshots as build artifacts.

## Notes
- The framework keeps the original working UI automation intact.
- API tests use JSONPlaceholder for stable, interview-friendly request/response validation.
- No unnecessary frameworks or infrastructure were introduced.
