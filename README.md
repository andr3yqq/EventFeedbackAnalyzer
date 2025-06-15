# Event Feedback Analyzer

This project is a Spring Boot application for analyzing feedback from events.
Also includes simple UI made with React.

## Setup

1.  **Clone the repository:**
    ```bash
    git clone <https://github.com/andr3yqq/EventFeedbackAnalyzer.git>
    cd eventFeedbackAnalyzer
    ```

2.  **Install dependencies:**
*   With Maven, run:
    ```bash
    mvn clean install
    ```

## Running the application

*   Using Maven:
    ```bash
    mvn spring-boot:run
    ```

The application will start on `http://localhost:8080`.

## API Endpoints

The following endpoints are available:

### Events

*   **GET /events**
    *   Retrieves a list of all events.

*   **GET /events/{eventId}**
    *   Retrieves a specific event by its ID.

*   **POST /events**
    *   Creates a new event.

*   **GET /events/{eventId}/summary**
    *   Retrieves a summary of the feedback for a specific event.
    
### Feedback

*   **GET /events/{eventId}/feedback**
    * Retrieves a list of all feedback for a specific event.
*   **GET /events/{eventId}/feedback/count**
    * Retrieves a number of feedback entries for a specific event.
*   **POST /events/{eventId}/feedback"**
    * Creates a new feedback for specific event.

## Optional
App comes with simple frontend UI built in React.

**Prerequisites**
* Node.js (ver 16+)
* npm

**Install dependencies**
```bash
  cd frontend
  npm install
```
**Run the application**
```bash
  npm start
```