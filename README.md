# 🧠 Quiz System - Microservices Architecture

This project is a **Microservices-based Quiz Application** built using **Spring Boot**, with the following major components:

- **Eureka Service Registry**
- **API Gateway (Spring Cloud Gateway)**
- **Question Service**
- **Quiz Service**
- **Feign Clients for inter-service communication**
  
![image](https://github.com/user-attachments/assets/9c3abbea-34e0-428e-85bf-7b887ba0fcf2)

---

## 📦 Microservices Overview

### 1. 🎯 Service Registry (Eureka)
- **Path:** `/`
- **Port:** `8761`
- **Technology:** `@EnableEurekaServer`
- **Role:** Acts as a **Service Registry** where all services register themselves for discovery.

---

### 2. 🚪 API Gateway
- **Port:** `8765`
- **Role:** Acts as a **single entry point** for all external calls to backend microservices.
- **Auto-discovery enabled**, it routes requests based on service name.

```properties
spring.application.name=api-gateway
server.port=8765
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

---

### 3. ❓ Question Service
- **Base path:** `/question`
- **Responsibilities:**
  - Manage questions data
  - Filter by category/difficulty
  - Generate quizzes with random questions
  - Evaluate submitted answers
    
![image](https://github.com/user-attachments/assets/2871ccb2-d65a-43cc-90df-5a6f276a0761)

#### 🔧 Main Endpoints:
- `GET /question/allQuestions` → Get all questions
- `GET /question/category/{category}` → Filter by category
- `GET /question/difficultyLevel/{level}` → Filter by difficulty
- `POST /question` → Add a new question
- `GET /question/create?category=X&numQuestions=Y` → Get random question IDs
- `POST /question/getQuestions` → Get questions by ID list
- `POST /question/getScore` → Evaluate answers

---

### 4. 📝 Quiz Service
- **Base path:** `/quiz`
- **Responsibilities:**
  - Create a quiz using questions from the Question Service
  - Submit answers to a quiz and get score

    ![image](https://github.com/user-attachments/assets/14646424-9e4c-4838-a081-85f9fb290e0c)


#### 🔧 Main Endpoints:
- `POST /quiz/create?category=X&numQ=Y&title=Z` → Create a quiz
- `GET /quiz/{id}` → Fetch quiz content
- `POST /quiz/submit/{id}` → Submit answers and get score

---

## 🔗 Inter-service Communication (via Feign)

`QuizService` uses **OpenFeign** to communicate with the `QuestionService`.

```java
@FeignClient("QUESTIONSERVICE")
public interface QuizInterface {
    @GetMapping("question/create")
    ResponseEntity<List<Integer>> createQuestionsForUser(@RequestParam String category,
                                                         @RequestParam int numQuestions);

    @PostMapping("question/getQuestions")
    ResponseEntity<List<QuestionDto>> getQuestionsByID(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getScore")
    ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
```

---

## 🛠️ How to Run the Project

### 1. Clone and Build
```bash
git clone https://github.com/your-repo/quiz-microservices.git
cd quiz-microservices
```

### 2. Start Services in order:
1. `ServiceRegistryApplication` → Port: `8761`
2. `ApiGatewayApplication` → Port: `8765`
3. `QuestionServiceApplication` → Port: `8081` (or as configured)
4. `QuizServiceApplication` → Port: `8082` (or as configured)

> All services should be registered in Eureka and reachable via API Gateway.

---

## ⚙️ Technologies Used

- Java 17+
- Spring Boot
- Spring Cloud (Eureka, Gateway, Feign)
- Lombok
- RESTful APIs
- Microservices Architecture

---

## 📌 Example Flow:

1. User hits:  
   `POST /quiz/create?category=java&numQ=5&title=SpringQuiz`

2. Quiz Service:
   - Calls Question Service to generate 5 random questions
   - Stores quiz metadata

3. User fetches quiz:  
   `GET /quiz/{id}`

4. User submits answers:  
   `POST /quiz/submit/{id}`

---

## 📬 Contact
For improvements or collaboration, feel free to fork or create a pull request 🤝

---

