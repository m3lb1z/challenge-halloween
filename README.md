# Proyecto API de Monstruos de Halloween 

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)


## Descripción 

Esta API permite la gestión de monstruos de Halloween, almacenando atributos como poderes, debilidades, apariciones y nivel de peligro. Cuenta con autenticación basada en **Spring Security Basic Authentication** y está documentada con **Swagger** para facilitar su uso y pruebas.

## Características
- **Registro y gestión de monstruos** con atributos personalizados.
- **Gestión de apariciones de monstruos** en diferentes lugares.
- **Autenticación con Spring Security Basic Authentication** para proteger las rutas.
- **Paginación** en las listas de monstruos.
- **Consultas personalizadas** por atributos como nombre, tipo, habilidades especiales, etc.
- **Estadísticas** de los monstruos por poder, tipo y nivel de peligro.
- **Swagger UI** para la documentación interactiva de la API.
  
## Endpoints Principales

### Monstruos
- **POST** `/monsters`: Registrar un nuevo monstruo.
- **GET** `/monsters`: Listar monstruos con paginación.
- **GET** `/monsters/{id}`: Obtener los detalles de un monstruo por su ID.
- **PUT** `/monsters/{id}`: Actualizar los datos de un monstruo.
- **DELETE** `/monsters/{id}`: Eliminar un monstruo.

### Apariciones
- **GET** `/monsters/{monsterId}/appearances`: Obtener las apariciones de un monstruo.
- **GET** `/monsters/{monsterId}/appearances/{id}`: Obtener una aparición específica de un monstruo.
- **POST** `/monsters/{monsterId}/appearances`: Crear una nueva aparición para un monstruo.
- **PUT** `/monsters/{monsterId}/appearances/{id}`: Actualizar una aparición de un monstruo.
- **DELETE** `/monsters/{monsterId}/appearances/{id}`: Eliminar una aparición de un monstruo.

### Búsquedas y Comparaciones
- **GET** `/monsters/type/{type}`: Filtrar monstruos por tipo.
- **GET** `/monsters/danger/{level}`: Filtrar monstruos por nivel de peligro.

## Autenticación 🔒

La API utiliza **Spring Security Basic Authentication** para asegurar las rutas. Los usuarios deben autenticarse proporcionando un username y password en el encabezado `Authorization` de cada petición.

**Usuarios:**
- **user:user**
- **admin:qwerty**

## Documentación con Swagger 📜

La documentación de la API está disponible en el endpoint `/swagger-ui.html`, donde puedes probar los diferentes endpoints y ver la estructura de los datos.


## Tecnologías Utilizadas 💻
- **Java 21**
- **Spring Boot**
- **Spring Security** para la autenticación.
- **Swagger** para la documentación de la API.
- **JPA** para la persistencia de datos.
- **PostgreSQL** para la base de datos.


## Pasos para ejecutar el proyecto 🚀

1. **Construir la imagen de Docker** para la aplicación.

    ```sh
    mvn clean package
    docker-compose build
    ```

2. **Ejecutar Docker Compose en segundo plano** para crear y ejecutar los contenedores.

    ```sh
    docker-compose up -d
    ```

3. **Verificar que los contenedores están en ejecución**.

    ```sh
    docker-compose ps
    ```
4. **Acceder a la documentación de la API** en `http://localhost:8080/swagger-ui.html`.