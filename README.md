## API REST Usarios y Agenda de contactos.

Api para crear nuevos usuarios y para actualizar la agenda de los mismos desde una aplicación movil.

---

Se ha utilizado el siguiente stack:

- Java 11, Gradle.
- Micronaut.
- MongoDB.
- Open Api.  
- Docker.

Para compilar el proyecto ejecutar el siguiente comando:

```shell
gradlew build
```

Para ejecutar los tests ejecutar el siguiente comando, indicando el tipo de tests.

```shell
gradlew [-Dtest.type=all|unit|integration] test
```

Para los tests de integración es necesario tener una base de datos MongoDB, para ello
se puede utilizar el docker-compose.yml para arrancar MongoDB con datos de prueba que se
encuentran en el fichero mongo-init.js.

```shell
docker-compose up -d mongodb
gradlew -Dtest.type=integration test
```

Después de ejecutar los tests de integración es conveniente eliminar el contenedor

```shell
docker stop mi-mongo
docker rm mi-mongo
```

Para levantar el proyecto ejectuar:

```shell
docker-compose up -d
```

Aunque he seguido la documentación de Swagger para Micronaut

https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html

no he conseguido acceder a la interfaz gráfica de Swagger, no obstante he incluido
una colección de Postman para poder probar los endpoints que se encuentra en la raiz del proyecto.

###### USERS.postman_collection.json.

#Estructura del proyecto

El proyecto tiene la siguiente estructura de paquetes bajo com.pablo.users

- client (Cliente Api Neutrino)
- config (Configuración validación, handlers y mongo)
  - handler (Manejadores de errores)
  - mongo (Factoría para colección usuarios)
  - validation (Factoría para validadores)
- controller
- domain (Entidades de dominio)
- exception (Excepciones de negocio)
- service (Lógica de negocio)

He intentado aplicar principios SOLID durante todo el desarrollo y desacoplar lo máximo posible para que cada
clase tenga su responsabilidad haciendo que el código sea fácilmente mantenible y testeable.
