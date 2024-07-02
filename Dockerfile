# Usar uma imagem base com Maven e JDK
FROM maven:3.8.4-openjdk-17-slim AS build

# Diretório de trabalho no contêiner
WORKDIR /app

# Copiar os arquivos pom.xml e o código-fonte
COPY pom.xml .
COPY src ./src

# Construir o projeto Maven
RUN mvn clean package -DskipTests

# Usar uma imagem base para o runtime
FROM openjdk:17

# Diretório de trabalho no contêiner
WORKDIR /app

# Copiar o jar construído para a imagem
COPY --from=build /app/target/*.jar app.jar

# Comando de inicialização do contêiner
ENTRYPOINT ["java", "-jar", "app.jar"]
