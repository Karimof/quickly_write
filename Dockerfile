
# 1-bosqich: kodni build qilish
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# 1.1. Dependency’larni cache qilish uchun avval pom.xml’ni nusxalaymiz
COPY pom.xml .
# 1.2. Kodni nusxalab olib, paketlaymiz
COPY src ./src
RUN mvn clean package -DskipTests

# 2-bosqich: tayyor JAR’ni yengil jre tasviriga o‘tkazamiz
FROM eclipse-temurin:17-jre
WORKDIR /app

# Builder’dan chiqadigan target papkasidagi JAR’ni ko‘chiramiz
COPY --from=builder /app/target/*.jar app.jar

# APK portingizni e’lon qilamiz (masalan, 9090)
EXPOSE 9090

# Ilovani ishga tushiramiz
ENTRYPOINT ["java", "-jar", "app.jar"]

