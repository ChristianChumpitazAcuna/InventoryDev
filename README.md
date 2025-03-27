# 🎛️ InventoryDev

**InventoryDev** es una aplicación desarrollada para la **Subgerencia de Informática y Estadística** de la **Municipalidad Distrital de Asia**. Su propósito es gestionar el inventario de equipos de cómputo de manera eficiente, permitiendo un control detallado de productos y componentes.

## 🏗️ Arquitectura

El proyecto adopta la **Arquitectura Hexagonal**, también conocida como **Ports and Adapters**, que promueve la separación de la lógica de negocio de las interfaces externas, facilitando la mantenibilidad y escalabilidad del sistema. Además, utiliza **Spring WebFlux** para aprovechar las capacidades de programación reactiva y manejar operaciones asíncronas de manera eficiente.

## 🛠️ Tecnologías Utilizadas

- **Backend:** Java con Spring Boot y Spring WebFlux
- **Base de Datos:** MongoDB
- **Contenedores:** Docker y Docker Compose

## 📦 Microservicios

Este repositorio corresponde al microservicio encargado de la gestión de **productos y componentes**. Está planificado implementar otro microservicio para la gestión del **personal encargado** del inventario.

## 📋 Requisitos Previos

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) o superior
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## 🚀 Instalación y Ejecución

### Configuración de Variables de Entorno

La aplicación utiliza la variable de entorno `DB_URI` para conectarse a la base de datos MongoDB. Asegúrate de configurar esta variable antes de ejecutar la aplicación.

### Ejecución con Maven

1. **Clona el repositorio:**

   ```sh
   git clone https://github.com/ChristianChumpitazAcuna/InventoryDev.git

## 🧪 Pruebas Unitarias

El proyecto incluye pruebas unitarias para los servicios principales, implementadas utilizando **JUnit 5** y **Mockito**. Estas pruebas aseguran que cada componente funcione correctamente de manera aislada, facilitando la detección temprana de errores y mejorando la calidad del código.

### Tecnologías de Pruebas Utilizadas

- **JUnit 5:** Framework de pruebas unitarias para Java que proporciona anotaciones y aserciones para la creación de tests.
- **Mockito:** Framework de simulación que permite crear objetos mock para probar componentes de manera aislada.

### Ubicación de las Pruebas

Las pruebas unitarias se encuentran en el directorio `src/test/java` del proyecto. Para ejecutarlas, puedes utilizar el siguiente comando de Maven:

```sh
mvn test

