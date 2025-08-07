# ⚡ MediFlow - Quick Start Guide

✅ Project Status: FULLY COMPLETED & Ready to Run

Get MediFlow running in 5 minutes with this quick start guide!

---

## 🚀 **1-Minute Local Setup**

### Prerequisites Check

```bash
# Verify you have the required tools
java -version    # Should be 17+
node -v         # Should be 18+
mvn -version    # Should be 3.8+
```

### Clone & Run

```bash
# 1. Clone the repository
git clone <your-repository-url>
cd hospital-management-system

# 2. Start all services (requires 6 terminals)
# Terminal 1 - Authorization Service
cd authorization-service && mvn spring-boot:run

# Terminal 2 - IPTreatment Offering Service  
cd iptreatment-offering-service && mvn spring-boot:run

# Terminal 3 - IPTreatment Service
cd iptreatment-service && mvn spring-boot:run

# Terminal 4 - Insurance Claim Service
cd insurance-claim-service && mvn spring-boot:run

# Terminal 5 - Swagger Service
cd swagger-service && mvn spring-boot:run

# Terminal 6 - Frontend Portal
cd member-portal && npm install && npm start
```

### Access the Application

- **🌐 Frontend**: <http://localhost:3000>
- **📚 API Docs**: <http://localhost:8084/swagger-ui.html>

---

## 🎯 **Quick Demo Workflow**

### Step 1: Login

1. Open <http://localhost:3000>
2. Enter any username (e.g., "admin")  
3. Click "Sign In" - JWT token generated automatically

### Step 2: Explore the System

1. **Dashboard** - View system metrics and service health
2. **Treatment Packages** - Browse Orthopaedics & Urology packages
3. **Specialists** - View Junior and Senior medical specialists  
4. **Treatments** - Create new patient treatment plans
5. **Claims** - Process insurance claims for treatments

### Step 3: Test API Endpoints

```bash
# Generate JWT Token
curl -X POST http://localhost:8080/auth/generate-token \
  -H "Content-Type: application/json" \
  -d '{"username": "admin"}'

# Use the token to access protected endpoints
curl -X GET http://localhost:8081/IPTreatmentPackages \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## 🏥 **Sample Data Available**

### Treatment Packages

- **Orthopaedics Package 1**: ₹2,500 (4 weeks) - Junior specialist
- **Orthopaedics Package 2**: ₹3,000 (6 weeks) - Senior specialist
- **Urology Package 1**: ₹4,000 (4 weeks) - Junior specialist  
- **Urology Package 2**: ₹5,000 (6 weeks) - Senior specialist

### Specialists  

- 2 Junior + 2 Senior Orthopaedics specialists
- 2 Junior + 2 Senior Urology specialists

### Insurance Providers

- Multiple pre-configured insurance companies
- Various coverage limits and policies

---

## 🔧 **Service Health Check**

Verify all services are running:

```bash
# Check service health
curl http://localhost:8080/auth/health      # Authorization
curl http://localhost:8081/actuator/health # IPTreatment Offering  
curl http://localhost:8082/actuator/health # IPTreatment
curl http://localhost:8083/actuator/health # Insurance Claim
curl http://localhost:8084/api/health      # Swagger
```

---

## 🎨 **UI Features to Try**

### Modern Design Elements

- **Smooth Animations** - Hover over cards and buttons
- **Theme Toggle** - Switch between light/dark modes
- **Responsive Layout** - Resize window to see mobile view
- **Loading States** - Watch smooth loading animations
- **Form Validation** - Try submitting empty forms

### Navigation

- **Sidebar Navigation** - Click menu items for smooth transitions
- **Mobile Menu** - Hamburger menu on small screens
- **Breadcrumbs** - Track your location in the app

---

## 🧪 **Run Tests**

### Backend Tests

```bash
# Test each service
cd authorization-service && mvn test
cd ../iptreatment-offering-service && mvn test  
cd ../iptreatment-service && mvn test
cd ../insurance-claim-service && mvn test
cd ../swagger-service && mvn test
```

### Frontend Tests  

```bash
cd member-portal && npm test
```

---

## 🚀 **Deploy to Cloud**

Ready to deploy? Follow the detailed deployment guide:

```bash
# See comprehensive deployment instructions
cat DEPLOYMENT.md
```

**Render.com Free Tier Ready** - All services optimized for free cloud deployment!

---

## ❓ **Troubleshooting**

### Port Conflicts

If ports are in use:

```bash
# Check what's using ports
lsof -i :8080  # Authorization Service
lsof -i :8081  # IPTreatment Offering  
lsof -i :8082  # IPTreatment
lsof -i :8083  # Insurance Claim
lsof -i :8084  # Swagger
lsof -i :3000  # Frontend

# Kill conflicting processes
kill -9 <PID>
```

### Build Issues

```bash
# Clean and rebuild
mvn clean package
npm ci  # Clean install for frontend
```

### Memory Issues

```bash
# Set lower memory for Java services
export JAVA_OPTS="-Xmx512m"
```

---

## 🎉 **You're Ready!**

**MediFlow is now running locally with:**

- ✅ Modern React frontend with Apple & Vercel-inspired UI
- ✅ 5 fully functional microservices  
- ✅ Complete authentication system
- ✅ Treatment management workflow
- ✅ Insurance claim processing
- ✅ API documentation
- ✅ Comprehensive test coverage

**Start exploring the system and managing international patient treatments!** 🏥✨

---

**Need help?** Check the full documentation in `README.md` or `PROJECT_COMPLETION.md`
