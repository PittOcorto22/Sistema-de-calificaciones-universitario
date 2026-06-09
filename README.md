# Sistema de Administración y Consulta de Calificaciones

## Descripción general

UniGrades JavaFX es un sistema de escritorio desarrollado con JavaFX para administrar y consultar calificaciones dentro de un entorno universitario. El sistema modela una universidad con carreras, semestres, materias, grupos, profesores, alumnos, inscripciones, configuraciones de evaluación y calificaciones.

El proyecto está diseñado para cumplir los requerimientos de Programación Orientada a Objetos, arquitectura por capas, patrón MVC, uso de patrones de diseño, persistencia de datos y desarrollo de interfaz gráfica.

El sistema permite que cada usuario acceda según su rol:
* **Administrador:** administra carreras, materias, profesores, alumnos, grupos, inscripciones y calificaciones.
* **Profesor:** visualiza sus materias y grupos asignados, registra y edita calificaciones.
* **Alumno:** consulta calificaciones parciales, actividades, promedio final y estatus académico.

## Objetivo del proyecto

Desarrollar una aplicación de escritorio funcional que permita gestionar la información académica de una universidad, aplicando buenas prácticas de desarrollo de software, separación de responsabilidades, persistencia de datos y control de acceso por roles.

## Funcionalidades principales

**Profesor**
* Iniciar sesión.
* Visualizar materias asignadas.
* Visualizar grupos asignados.
* Registrar calificaciones parciales.
* Registrar calificación de actividades.
* Editar calificaciones.
* Calcular promedio final automáticamente.

**Alumno**
* Iniciar sesión.
* Consultar calificaciones parciales.
* Consultar calificación de actividades.
* Consultar calificación final.
* Consultar estatus: aprobado o reprobado.

**Administrador**
* Iniciar sesión.
* Administrar carreras.
* Administrar materias.
* Administrar profesores.
* Administrar alumnos.
* Administrar grupos.
* Editar calificaciones ingresadas.
* Configurar reglas de evaluación por materia.
* Validar que los porcentajes de evaluación sumen 100%.

## Tecnologías utilizadas

| Tecnología | Uso dentro del proyecto |
| :--- | :--- |
| **Java 17+** | Lenguaje principal del sistema |
| **JavaFX** | Interfaz gráfica de escritorio |
| **Maven** | Administración del proyecto y dependencias |
| **MySQL** | Persistencia local de datos (Base de datos relacional) |
| **MVC** | Separación entre interfaz, controladores, servicios y modelos |
| **Singleton** | Manejo de sesión activa |
| **Strategy** | Cálculo flexible del promedio final |
| **Repository** | Separación del acceso a datos |

## Usuarios de prueba

Puedes utilizar las siguientes credenciales para probar el sistema:

| Rol | Correo | Contraseña |
| :--- | :--- | :--- |
| **Administrador** | admin@udelp.edu.mx | root |
| **Profesor** | carlos@udelp.edu.mx | admin123 |
| **Alumno** | juan@udelp.edu.mx | 1234 |

---

##  Cómo ejecutar el proyecto

### Requisitos
* Java JDK 17 o superior.
* Maven instalado.
* MySQL Server y MySQL Workbench instalados y corriendo.
* Conexión a internet la primera vez para descargar dependencias JavaFX.

### Paso 1: Preparar la Base de Datos en MySQL
El proyecto requiere que las tablas existan en tu servidor local. Abre el archivo `universidad_db.sql` en **MySQL Workbench**. 

**IMPORTANTE:** Antes de ejecutar el script, haz estas 2 modificaciones en el código para evitar errores de importación:
1.  **Crear la BD:** En la Línea 1, agrega estas dos instrucciones:
    ```sql
    CREATE DATABASE IF NOT EXISTS universidad_db;
    USE universidad_db;
    ```
2.  **Evitar rechazo de servidor:** Busca (cerca de la línea 23) una instrucción que empieza con `SET @@GLOBAL.GTID_PURGED...` y **bórrala** por completo. 

Ejecuta el script completo (haz clic en el rayo amarillo) para crear las tablas.

### Paso 2: Configurar las Credenciales
1.  En IntelliJ IDEA, abre el archivo: `src/main/java/com/edu/udelp/sistemaCalificaciones/db/conexion.java`
2.  Cambia la contraseña por la de tu propio servidor MySQL local:
    ```java
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universidad_db", "root", "tu_contraseña_aqui");
    ```

### Paso 3: Ejecución con Maven
Desde IntelliJ, abre el panel de Maven y recarga el proyecto (Reload project) para descargar JavaFX. Luego ejecuta la clase `Lanzador.java` o ejecuta en consola:
`mvn clean javafx:run`

---

##  Solución de problemas comunes 
Si utilizas una Mac con procesadores Apple Silicon (M1, M2, M3...) y la ventana se cierra inesperadamente arrojando un error de `libglass.dylib`, aplica este parche rápido para apagar la aceleración gráfica:
1.  En IntelliJ, ve a **Run -> Edit Configurations...**
2.  En el campo **VM options**, pega este comando: `-Dprism.order=sw`
3.  Aplica los cambios y vuelve a ejecutar.

---

## Estructura principal del repositorio

```text
unigrades_javafx_sistema_calificaciones/
├── README.md
├── GITHUB_SETUP.md
├── pom.xml
├── .gitignore
├── data/
├── docs/
├── diagrams/
├── scripts/
├── src/
└── tests/
