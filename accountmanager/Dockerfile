FROM java:8-jdk-alpine
WORKDIR /usr/src/
ENV MYSQL_DATABASE=muzix_db
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/muzix_db
COPY ./target/AccountManager.jar /usr/src/AccountManager.jar
CMD java -jar AccountManager.jar
