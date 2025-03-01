# Gerimedica Assignment

What changed?
- Folder structure added
- Functionality stays the same with more efficient and cleaner code inside of all classes.
- Response objects chaned to DTO's.
- Exception handling added.
- H2 console added.
- Swagger added.
- Usage of Lombok.

Access Swagger after starting the application : http://localhost:8080/swagger-ui/index.html#/

Access H2 Console after starting the application : 
- URL : http://localhost:8080/h2-console/login.jsp
- JDBC URL : jdbc:h2:mem:assignment
- User Name : root
- Password : root

Controller :
- Changed to a more readeble class without complex operations.
- Mappings changed.

Service :
- Service class separeted as HospitalService and HospitalServiceImpl which provides loose coupling.
- Service operations changed with short and cleaner code with new features of Java.
- Usage of Optional class to handle exceptions and operations.

Models :
- Boilerplate codes removed with Lombok usage.
- Changed all fields as private to prevent direct access.
- Records used for DTO objects to get rid of unnecessary code.
- Appointment's lazy fetch type removed because of data loss when calling the API.
- JsonIgnore added to Patient entity to prevent data coming as nested loop.

Exceptions :
- Global exception handling added.  

Converter : 
- A converter added to use DTO instead of directly entity.
