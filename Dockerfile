FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copie primeiro o pom.xml para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o código fonte
COPY src ./src

# Compile a aplicação
RUN mvn clean package -DskipTests

# Use uma imagem menor para runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copie o JAR construído
COPY --from=0 /app/target/serratec-0.0.1-SNAPSHOT.jar app.jar

# Adicione usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]