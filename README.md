# 🏥 MediFlow - International Patient Treatment Management System

**MediFlow** is a comprehensive microservices-based hospital management system designed for international patient treatment management, featuring seamless workflow for treatment packages, specialist assignments, and insurance claim processing with a modern Apple & Vercel-inspired UI.

## ✅ **PROJECT STATUS: FULLY COMPLETED**

This system has been fully implemented with production-ready code including:

- ✅ Complete React frontend with modern UI/UX design
- ✅ All 5 microservices fully functional
- ✅ Comprehensive test coverage
- ✅ API documentation with Swagger
- ✅ Cloud deployment ready
- ✅ Security implementation with JWT

## 🚀 System Overview

MediFlow consists of 5 microservices and a modern React frontend:

- **Authorization Service** (Port 8080) - JWT token management ✅ **COMPLETE**
- **IPTreatmentOffering Service** (Port 8081) - Treatment packages & specialists ✅ **COMPLETE**
- **IPTreatment Service** (Port 8082) - Treatment timetable generation ✅ **COMPLETE**
- **InsuranceClaim Service** (Port 8083) - Insurance processing ✅ **COMPLETE**
- **Swagger Service** (Port 8084) - API documentation aggregation ✅ **COMPLETE**
- **MediFlow Portal** - React frontend with Material-UI ✅ **COMPLETE**

## 🎨 **Modern UI Features - Apple & Vercel Inspired**

- **Clean Design System** - Minimalist Apple-inspired aesthetics
- **Smooth Animations** - Vercel-style hover effects and transitions
- **Modern Components** - Glass-morphism cards, gradient backgrounds
- **Responsive Layout** - Mobile-first design with smooth breakpoints
- **Dark/Light Theme** - Toggle between themes with system preference detection
- **Professional Typography** - Inter font with perfect spacing and hierarchy

## 🛠️ Technology Stack

### Backend

- **Java 17**
- **Spring Boot 3.4.8**
- **Spring Security** with JWT
- **Spring Data JPA**
- **H2 In-Memory Database**
- **Maven** for build management
- **JUnit 5 & Mockito** for testing
- **Swagger/OpenAPI 3** for documentation

### Frontend

- **React 19** with TypeScript
- **Material-UI v7** for components with custom theming
- **React Router v6** for navigation and protected routes
- **React Query (TanStack Query)** for server state management
- **React Hook Form** with Yup validation
- **Axios** with interceptors for API calls
- **Jest & React Testing Library** for testing
- **Custom Theme System** - Apple & Vercel design patterns

## 🌟 **Key Features Implemented**

### 🔐 Authentication & Security

- JWT-based authentication with 30-minute expiry
- Protected routes with automatic redirects
- Secure API endpoints with Bearer token validation
- CORS configuration for cross-origin requests

### 👨‍⚕️ Treatment Management

- **Treatment Packages** - Orthopaedics & Urology specializations
- **Specialist Assignment** - Automated Junior/Senior assignment based on package tier
- **Treatment Timetables** - Automated generation with specialist scheduling
- **Patient Registration** - Complete patient detail management

### 💰 Insurance Processing

- **Claim Initiation** - Easy claim submission with treatment cost calculation
- **Insurance Provider Management** - Multiple insurer support
- **Claim Status Tracking** - Real-time status updates (Initiated/Approved/Rejected)
- **Financial Calculations** - Automatic claim amount determination

### 📊 Dashboard & Analytics

- **System Overview** - Real-time metrics and statistics
- **Service Health Monitoring** - All microservices status tracking
- **Performance Indicators** - Treatment success rates and system utilization
- **Interactive Charts** - Visual representation of key data points

### 🎨 Modern UI/UX

- **Apple-Inspired Design** - Clean, minimal aesthetic with subtle shadows
- **Vercel-Style Animations** - Smooth hover effects and transitions
- **Responsive Design** - Mobile-first approach with perfect breakpoints
- **Dark/Light Theme** - Toggle with system preference detection
- **Professional Navigation** - Collapsible sidebar with smooth animations

### 📱 User Experience

- **Intuitive Workflow** - Step-by-step treatment planning process
- **Real-time Feedback** - Loading states and success/error notifications
- **Form Validation** - Comprehensive input validation with helpful error messages
- **Accessibility** - WCAG compliant with keyboard navigation support

## 📋 Pre-defined Data

### Treatment Packages

| Specialization | Package | Tests | Cost | Duration |
|---------------|---------|-------|------|----------|
| Orthopaedics | Package 1 | OPT1, OPT2 | ₹2,500 | 4 weeks |
| Orthopaedics | Package 2 | OPT3, OPT4 | ₹3,000 | 6 weeks |
| Urology | Package 1 | UPT1, UPT2 | ₹4,000 | 4 weeks |
| Urology | Package 2 | UPT3, UPT4 | ₹5,000 | 6 weeks |

### Specialists

- 2 Junior Specialists per specialization
- 2 Senior Specialists per specialization
- Package 1 → Junior Specialist assignment
- Package 2 → Senior Specialist assignment

## 🏃‍♂️ Quick Start

### Prerequisites

- **Java 17+**
- **Node.js 18+**
- **Maven 3.8+**
- **Git**

### Local Development

1.**Clone the repository**

```bash
git clone <repository-url>
cd hospital-management-system
```

2.**Start services in order:**

```bash
# Terminal 1 - Authorization Service
cd authorization-service
mvn spring-boot:run

# Terminal 2 - IPTreatmentOffering Service  
cd iptreatment-offering-service
mvn spring-boot:run

# Terminal 3 - IPTreatment Service
cd iptreatment-service
mvn spring-boot:run

# Terminal 4 - InsuranceClaim Service
cd insurance-claim-service
mvn spring-boot:run

# Terminal 5 - Swagger Service
cd swagger-service
mvn spring-boot:run

# Terminal 6 - Frontend
cd member-portal
npm install
npm start
```

### Service URLs (Local)

- 🔐 **Authorization Service**: <http://localhost:8080>
- 📦 **IPTreatmentOffering Service**: <http://localhost:8081>
- ⏰ **IPTreatment Service**: <http://localhost:8082>
- 🏥 **InsuranceClaim Service**: <http://localhost:8083>
- 📚 **Swagger Service**: <http://localhost:8084>
- 🌐 **MediFlow Portal**: <http://localhost:3000>

## ☁️ Cloud Deployment (Render)

### Backend Services Deployment

Each microservice can be deployed to Render's free tier:

1. **Create Render account** at <https://render.com>
2. **Connect GitHub repository**
3. **Deploy each service** with these settings:

```yaml
# render.yaml for each service
services:
  - type: web
    name: mediflow-auth-service
    env: java
    buildCommand: mvn clean package -DskipTests
    startCommand: java -jar target/*.jar
    envVars:
      - key: JAVA_TOOL_OPTIONS
        value: -Xmx512m -Dserver.port=$PORT
```

### Frontend Deployment

Deploy React frontend to Render Static Sites:

```yaml
# render.yaml for frontend
services:
  - type: web
    name: mediflow-portal
    env: node
    buildCommand: npm ci && npm run build
    startCommand: npm start
    envVars:
      - key: REACT_APP_API_BASE_URL
        value: https://your-backend-url.render.com
```

### Environment Configuration for Free Tier

To optimize for Render's free tier limitations:

```yaml
# application.yml optimizations
spring:
  datasource:
    hikari:
      maximum-pool-size: 5
      minimum-idle: 1
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false
    
server:
  tomcat:
    threads:
      max: 50
      min-spare: 5
```

## 🧪 Testing

### Run All Tests

```bash
# Backend tests with coverage (run from each service directory)
cd authorization-service && mvn clean test jacoco:report
cd ../iptreatment-offering-service && mvn clean test jacoco:report
cd ../iptreatment-service && mvn clean test jacoco:report
cd ../insurance-claim-service && mvn clean test jacoco:report
cd ../swagger-service && mvn clean test jacoco:report

# Frontend tests
cd member-portal
npm test -- --coverage --watchAll=false
```

### Test Coverage Status

- **Authorization Service**: ✅ **COMPLETE** - Full controller, service, and util test coverage
- **IPTreatment Offering Service**: ✅ **COMPLETE** - Repository and service tests implemented
- **IPTreatment Service**: ✅ **COMPLETE** - Controller and service test coverage
- **Insurance Claim Service**: ✅ **COMPLETE** - Full test suite with repository tests
- **Swagger Service**: ✅ **COMPLETE** - Controller and application context tests
- **Frontend**: ✅ **COMPLETE** - Component testing setup with Jest & RTL

## 🔑 API Authentication

1.**Get JWT Token:**

```bash
curl -X POST http://localhost:8080/auth/generate-token \
  -H "Content-Type: application/json" \
  -d '{"username": "admin"}'
```

2.**Use Token in Requests:**

```bash
curl -X GET http://localhost:8081/IPTreatmentPackages \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📊 API Documentation

Access Swagger UI at:

- **Local**: <http://localhost:8084/swagger-ui.html>
- **Cloud**: <https://your-swagger-service.render.com/swagger-ui.html>

## 🔧 Configuration

### Database Configuration

All services use H2 in-memory database for lightweight deployment:

- **Console**: Available at `/h2-console` endpoint
- **Auto-initialization**: Predefined data loaded on startup

### Security Configuration

- **JWT expiry**: 30 minutes
- **Anonymous access**: Only for token generation
- **CORS**: Configured for cross-origin requests

## 📁 Project Structure

```text
hospital-management-system/
├── authorization-service/              # ✅ JWT token management
│   ├── src/main/java/                 # Main application code
│   ├── src/test/java/                 # Comprehensive test suite
│   └── pom.xml                        # Maven configuration
├── iptreatment-offering-service/      # ✅ Treatment packages & specialists
│   ├── src/main/java/                 # Service implementation
│   ├── src/test/java/                 # Unit and integration tests
│   └── pom.xml                        # Dependencies
├── iptreatment-service/               # ✅ Treatment scheduling
│   ├── src/main/java/                 # Timetable generation logic
│   ├── src/test/java/                 # Service tests
│   └── pom.xml                        # Maven build
├── insurance-claim-service/           # ✅ Insurance processing
│   ├── src/main/java/                 # Claim management system
│   ├── src/test/java/                 # Complete test coverage
│   └── pom.xml                        # Spring Boot config
├── swagger-service/                   # ✅ API documentation aggregation
│   ├── src/main/java/                 # Documentation service
│   ├── src/test/java/                 # API documentation tests
│   └── pom.xml                        # OpenAPI dependencies
├── member-portal/                     # ✅ React frontend
│   ├── src/
│   │   ├── components/layout/         # Header, Sidebar, Layout
│   │   ├── pages/                     # All application pages
│   │   ├── services/                  # API service layer
│   │   ├── theme/                     # Custom Material-UI theme
│   │   └── types/                     # TypeScript definitions
│   ├── public/                        # Static assets
│   ├── package.json                   # React dependencies
│   └── tsconfig.json                  # TypeScript configuration
├── DEPLOYMENT.md                      # ✅ Cloud deployment guide
├── render.yaml                        # ✅ Render deployment config
└── README.md                          # This comprehensive guide
```

## 🚦 Health Checks

All services provide health endpoints:

- `GET /actuator/health` - Service health status
- `GET /auth/health` - Authorization service specific

## 🐛 Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure all required ports are available
2. **Memory issues**: Reduce JVM heap size for free tier deployment
3. **CORS errors**: Verify frontend API base URL configuration
4. **JWT expiry**: Token expires after 30 minutes, get new token

### Logs

- **Local development**: Console output with DEBUG level
- **Production**: Configured for INFO level to reduce resource usage

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Support

For support and queries:

- 📧 Email: <support@mediflow.com>
- 📚 Documentation: Available in `/docs` folder
- 🐛 Issues: GitHub Issues tab

---

## 🎯 **Implementation Status**

### ✅ **COMPLETED FEATURES**

- [x] **Frontend Application** - Complete React app with modern UI
- [x] **Authentication System** - JWT-based login with protected routes
- [x] **Treatment Management** - Package selection and specialist assignment
- [x] **Insurance Processing** - Claim initiation and tracking
- [x] **API Documentation** - Swagger UI for all services
- [x] **Test Coverage** - Comprehensive unit and integration tests
- [x] **Responsive Design** - Mobile-first with Apple & Vercel aesthetics
- [x] **Data Management** - Complete CRUD operations for all entities
- [x] **Error Handling** - Graceful error boundaries and API error handling
- [x] **Build Pipeline** - Production-ready builds for all components

### 🚀 **READY FOR DEPLOYMENT**

- [x] **Local Development** - All services run locally with proper configuration
- [x] **Cloud Deployment** - Render.com configuration files included
- [x] **Environment Management** - Separate dev/test/prod configurations
- [x] **Security** - JWT authentication, CORS, input validation
- [x] **Monitoring** - Health checks and actuator endpoints

### 🎨 **UI/UX HIGHLIGHTS**

- **Modern Design** - Apple-inspired clean aesthetic with Vercel-style animations
- **Professional Navigation** - Collapsible sidebar with smooth state transitions
- **Interactive Components** - Hover effects, loading states, success/error feedback
- **Theme System** - Custom Material-UI theme with dark/light mode support
- **Typography** - Inter font with perfect spacing and visual hierarchy

### 💡 **TECHNICAL HIGHLIGHTS**

- **Microservices Architecture** - 5 independent services with clear separation of concerns
- **Modern Frontend Stack** - React 19, TypeScript, Material-UI v7, React Query
- **Spring Boot Backend** - Java 17, Spring Security, JPA, H2 Database
- **Testing Strategy** - JUnit 5, Mockito, Jest, React Testing Library
- **API Design** - RESTful APIs with OpenAPI 3.0 documentation

---

Built with ❤️ for seamless international patient treatment management

Project completed with production-ready code and modern design principles
