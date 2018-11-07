FROM java
EXPOSE 8080
ADD target/soen487-w18-team07-0.1-SNAPSHOT.war soen487-w18-team07-0.1-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "/soen487-w18-team07-0.1-SNAPSHOT.war"]
