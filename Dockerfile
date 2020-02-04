FROM maven:latest as build
WORKDIR /app
COPY ./ /app
RUN ["mvn", "clean", "package", "spring-boot:repackage"]

FROM openjdk:8-jre
WORKDIR /app
COPY --from=build /app /app
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar target/app-1.0.jar"]