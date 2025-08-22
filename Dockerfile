FROM maven:3.9.9-eclipse-temurin-17-alpine

WORKDIR /app

# Copie tudo
COPY . .

# Compile a aplicação
RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/serratec-0.0.1-SNAPSHOT.jar"]