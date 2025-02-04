# 🏥 HBM Backend API - ECG Monitoring System

Welcome to **HBM Backend API**! This project is a **Hexagonal Architecture-based** backend system for processing ECG (Electrocardiogram) measurements in real-time, detecting anomalies, and notifying irregularities through **WebSockets**.

This backend interacts with an **ECG measurement generator** and **WebSocket clients** (e.g., a frontend dashboard) to monitor heart conditions. ❤️📊

## 🚀 Features

- ✅ **Hexagonal Architecture**: Clean domain-driven design (DDD).
- 📡 **Real-time ECG Processing**: Receives and processes ECG measurements.
- ⚠️ **Anomaly Detection**: Detects abnormal ECG values and classifies irregularities as `BIP` or `BIP_BIP`.
- 📁 **MongoDB Atlas Integration**: Stores measurements and irregularity events.
- 🔄 **WebSocket Communication**: Streams ECG measurements and anomalies to the frontend.
- 🏗️ **RESTful API**: Provides historical measurement retrieval.
- 🛠️ **Unit Tests**: High test coverage using **JUnit** and **Mockito**.
- 🔍 **Swagger API Documentation**: Auto-generated API documentation.

## 🏗️ Project Structure

```
📂 hbm-backend
 ├── 📁 src
 │   ├── 📁 main
 │   │   ├── 📁 java
 │   │   │   ├── 📁 com.hbm.mandis.hbm_backend
 │   │   │   │   ├── 📁 adapters
 │   │   │   │   │   ├── 📁 in  # Incoming adapters (Controllers)
 │   │   │   │   │   │   ├── 📁 controllers
 │   │   │   │   │   │   ├   ├── 📁 dto
 │   │   │   │   │   │   ├── 📁 exceptions
 │   │   │   │   │   ├── 📁 out  # Outgoing adapters (Database/WebSocket)
 │   │   │   │   │   │   ├── 📁 client
 │   │   │   │   │   │   ├── 📁 mapper
 │   │   │   │   │   │   ├── 📁 model
 │   │   │   │   │   │   ├── 📁 repository
 │   │   │   │   │   │   ├── 📁 websocket
 │   │   │   │   │   │   ├── IrregularityEventPersistenceAdapter.java
 │   │   │   │   │   │   ├── MeasurementPersistenceAdapter.java
 │   │   │   │   ├── 📁 config
 │   │   │   │   │   ├── 📁 ports
 │   │   │   │   │   │   ├── PortInjection.java
 │   │   │   │   │   ├── 📁 schedulers
 │   │   │   │   │   ├── 📁 websocket
 │   │   │   │   │   ├── SpringdocConfiguration.java
 │   │   │   │   │   ├── WebClientConfig.java
 │   │   │   │   ├── 📁 core
 │   │   │   │   │   ├── 📁 domain  # Business logic
 │   │   │   │   │   │   ├── 📁 exceptions
 │   │   │   │   │   │   ├── 📁 models
 │   │   │   │   │   ├── 📁 ports  # Hexagonal architecture ports
 │   │   │   │   │   │   ├── 📁 in
 │   │   │   │   │   │   │   ├── measurement
 │   │   │   │   │   │   ├── 📁 out
 │   │   │   │   │   ├── 📁 usecase
 │   │   │   │   │   │   ├── measurement
 │   │   │   │   ├── HbmBackendApplication.java
 │   ├── 📁 resources
 │   │   ├── application.yml  # ⚠️ Contains database credentials (for local testing only!)
 │   ├── 📁 test  # Unit tests (JUnit + Mockito)
```

## 📁 Dependencies

This backend interacts with the following external components:

- Frontend Dashboard: Displays real-time ECG measurements. Repository link: Frontend ECG Dashboard

- Heartbeat Generator API: Simulates ECG measurements and sends them via WebSocket. Repository link: Heartbeat Generator

## 📡 API Endpoints

### **ECG Measurement Processing**

#### **Receive New ECG Measurement**
> **WARNING:** This endpoint is intended solely for development testing purposes. Measurements are received via WebSocket.
- **Endpoint:** `POST /api/measurements`
- **Description:** Receives an ECG measurement, processes it, and detects anomalies.
- **Request Body:**
```json
{
  "value": 1.0,
  "timestamp": "2024-02-03T12:00:00Z",
  "deviceId": "HBM-12345"
}
```
- **Response:** `200 OK`

#### **Retrieve Last 30 Days of ECG Measurements**
- **Endpoint:** `GET /api/measurements/history/{deviceId}`
- **Description:** Returns the measurement history for the last 30 days.
- **Response:**
```json
[
  {
    "value": 1.1,
    "timestamp": "2024-01-15T10:30:00Z",
    "deviceId": "HBM-12345",
    "anomaly": false
  }
]
```

### **WebSocket Streaming**
- **WebSocket Endpoint:** `/ws/measurements`
- **Sends ECG measurements to connected clients in real-time.**
- **Anomalies trigger immediate alerts (BIP/BIP_BIP notifications).**

## 🛠️ Running the Project

### **1️⃣ Prerequisites**
- 🏗️ **Java 17+**
- 🛢️ **MongoDB Atlas** (or local MongoDB instance)
- 🌐 **Postman/WebSocket Client** for testing

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/hbm-backend.git
cd hbm-backend
```

### **3️⃣ Configure MongoDB Atlas** ⚠️
> **WARNING:** The `application.yml` currently contains **database credentials** for local testing! This is **not secure** for production. The ideal solution is to use **environment variables** or a **secret management tool**.

Modify `src/main/resources/application.yml`:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://your_user:your_password@your_cluster.mongodb.net/hbm_db
```

### **4️⃣ Run the Application** 🚀
```sh
./mvnw spring-boot:run  # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

### **5️⃣ Open API Documentation (Swagger UI)** 🌍
Once running, access:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## ✅ Unit Tests

The project includes **JUnit + Mockito** unit tests to ensure reliability.

### **Run all tests**
```sh
mvn test
```

### **Example Test Case (SaveMeasurementUsecaseTest)**
```java
@Test
void shouldSaveNormalMeasurement() {
    when(measurementPersistenceOutputPort.getLast60Measurements(anyString()))
        .thenReturn(Collections.emptyList());
    when(measurementPersistenceOutputPort.save(any())).thenReturn(normalMeasurement);

    EcgMeasurementModel result = saveMeasurementUsecase.save(normalMeasurement);

    assertNotNull(result);
    assertFalse(result.isAnomaly());
    verify(measurementPersistenceOutputPort, times(1)).save(any());
}
```

## 🚀 Future Improvements

🔹 **✅ Dockerize the Application**
- Use **Docker Compose** to containerize the backend and MongoDB Atlas.
- **Current DB credentials are hardcoded** (only for local testing). Move them to **environment variables**.

🔹 **📡 WebSocket Authentication**
- Implement token-based authentication for WebSocket connections.

🔹 **📊 Add GraphQL Support**
- Enable GraphQL for more flexible data retrieval.

🔹 **📈 Dashboard Frontend**
- Develop a real-time dashboard to visualize ECG measurements.

---

## 🎯 Contributors
👤 **Amanda Castro** | [Portfolio](https://mandis.framer.website/)

---

## ⚖️ License
This project is **MIT Licensed**. See [LICENSE](LICENSE) for details.

---

✅ **If you found this project useful, don't forget to ⭐ the repo!** 🚀🔥

