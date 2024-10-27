# Usar la imagen base de JRE 21 Alpine
FROM openjdk:21-jdk

# Crear el grupo y el usuario
RUN groupadd -r devops && useradd -r -g devops admin

# Crear un directorio para la aplicación
#WORKDIR /app
VOLUME /tmp

RUN chown -R admin:devops /tmp

# Establecer el usuario no root
USER admin

# Copiar el archivo JAR en el contenedor
ARG JAR_FILE="target/*.jar"
COPY ${JAR_FILE} /tmp/app.jar

# Exponer el puerto en el que se ejecutará la aplicación
#EXPOSE 8080

# Ejecutar la aplicación
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /tmp/app.jar"]