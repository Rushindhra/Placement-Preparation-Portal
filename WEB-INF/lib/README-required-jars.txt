Place these dependency JAR files in this WEB-INF/lib folder before compiling/running:

1. mysql-connector-j-8.x.x.jar
   Required for com.mysql.cj.jdbc.Driver.

2. jbcrypt-0.4.jar
   Required for org.mindrot.jbcrypt.BCrypt.

Tomcat provides servlet-api.jar from its own lib folder. Do not copy servlet-api.jar into WEB-INF/lib.
