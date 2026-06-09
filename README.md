# Sistema de Administración y Consulta de Calificaciones

## Descripción general

**UniGrades JavaFX** es un sistema de escritorio desarrollado con **JavaFX** para administrar y consultar calificaciones dentro de un entorno universitario. El sistema modela una universidad con carreras, semestres, materias, grupos, profesores, alumnos, inscripciones, configuraciones de evaluación y calificaciones.

El proyecto está diseñado para cumplir los requerimientos de Programación Orientada a Objetos, arquitectura por capas, patrón MVC, uso de patrones de diseño, persistencia de datos y desarrollo de interfaz gráfica.

El sistema permite que cada usuario acceda según su rol:

- **Administrador:** administra carreras, materias, profesores, alumnos, grupos, inscripciones y calificaciones.
- **Profesor:** visualiza sus materias y grupos asignados, registra y edita calificaciones.
- **Alumno:** consulta calificaciones parciales, actividades, promedio final y estatus académico.

---

## Objetivo del proyecto

Desarrollar una aplicación de escritorio funcional que permita gestionar la información académica de una universidad, aplicando buenas prácticas de desarrollo de software, separación de responsabilidades, persistencia de datos y control de acceso por roles.

---

## Funcionalidades principales

### Profesor

- Iniciar sesión.
- Visualizar materias asignadas.
- Visualizar grupos asignados.
- Registrar calificaciones parciales.
- Registrar calificación de actividades.
- Editar calificaciones.
- Calcular promedio final automáticamente.

### Alumno

- Iniciar sesión.
- Consultar calificaciones parciales.
- Consultar calificación de actividades.
- Consultar calificación final.
- Consultar estatus: aprobado o reprobado.

### Administrador

- Iniciar sesión.
- Administrar carreras.
- Administrar materias.
- Administrar profesores.
- Administrar alumnos.
- Administrar grupos.
- Editar calificaciones ingresadas.
- Configurar reglas de evaluación por materia.
- Validar que los porcentajes de evaluación sumen 100%.

---

## Tecnologías utilizadas

| Tecnología | Uso dentro del proyecto |
|---|---|
| Java 17+ | Lenguaje principal del sistema |
| JavaFX | Interfaz gráfica de escritorio |
| Maven | Administración del proyecto y dependencias |
| XML | Persistencia local de datos |
| MVC | Separación entre interfaz, controladores, servicios y modelos |
| Singleton | Manejo de sesión activa |
| Strategy | Cálculo flexible del promedio final |
| Repository | Separación del acceso a datos |

---

## Usuarios de prueba

Al ejecutar el sistema por primera vez se crea automáticamente un archivo XML con datos iniciales.

| Rol | Correo | Contraseña |
|---|---|---|
| Administrador | admin@universidad.edu | admin123 |
| Profesor | profesor@universidad.edu | profe123 |
| Alumno | alumno@universidad.edu | alumno123 |

---

## Cómo ejecutar el proyecto

### Requisitos

- Java JDK 17 o superior.
- Maven instalado.
- Conexión a internet la primera vez para descargar dependencias JavaFX.

### Ejecución con Maven

Desde la raíz del proyecto:

```bash
mvn clean javafx:run
```

### Compilar el proyecto

```bash
mvn clean package
```

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
```

---

## Explicación breve de carpetas

### `src/`

Contiene el código fuente del sistema. Está dividido por paquetes para mantener la arquitectura ordenada:

- `model`: entidades del sistema.
- `repository`: acceso a datos.
- `persistence`: lectura y escritura XML.
- `service`: reglas de negocio y operaciones académicas.
- `controller`: coordinación entre interfaz y servicios.
- `ui`: pantallas JavaFX.
- `pattern`: implementación de patrones de diseño.
- `util`: validaciones, alertas y utilidades.

### `data/`

Contiene el archivo XML donde se guardan los datos del sistema. Si el archivo no existe, se crea automáticamente con información de prueba.

### `docs/`

Contiene documentación por fases, explicación del modelo de datos, arquitectura, patrones de diseño, persistencia, manual de usuario, pruebas y defensa individual.

### `diagrams/`

Contiene diagramas en Mermaid, DOT y PNG:

- Modelo Entidad-Relación.
- Diagrama UML de clases.
- Flujo de login y roles.
- Arquitectura MVC por capas.

### `scripts/`

Contiene comandos de apoyo para ejecutar el sistema y subirlo a GitHub.

### `tests/`

Contiene pruebas conceptuales y casos de validación manual para demostrar que el sistema controla errores y evita fallos durante la evaluación.

---

## Arquitectura del sistema

El sistema utiliza una arquitectura tipo MVC con separación por capas:

```text
UI JavaFX
   ↓
Controllers
   ↓
Services
   ↓
Repositories
   ↓
XML Persistence
```

Esta estructura evita que la interfaz gráfica esté acoplada directamente con la lógica de negocio o la persistencia de datos.

---

## Patrones de diseño implementados

### Singleton

Se utiliza en `SessionManager` para mantener un único usuario autenticado durante la ejecución.

### Strategy

Se utiliza en el cálculo de calificaciones mediante `GradeCalculatorStrategy`. Esto permite cambiar la fórmula de promedio final sin modificar las pantallas.

### Repository

Se utiliza para separar la lógica de acceso a datos de la lógica de negocio.

---

## Cálculo automático de promedio final

Cada materia tiene una configuración de evaluación con:

- Porcentaje de parciales.
- Porcentaje de actividades.
- Calificación mínima aprobatoria.
- Límite de faltas.

La fórmula general usada es:

```text
promedioFinal = promedioParciales * porcentajeParciales + actividades * porcentajeActividades
```

Los porcentajes se transforman a decimal. Por ejemplo, 70% se utiliza como 0.70.

Ejemplo:

```text
Parcial 1 = 8.0
Parcial 2 = 9.0
Parcial 3 = 7.0
Actividades = 10.0
Porcentaje parciales = 70%
Porcentaje actividades = 30%

Promedio parciales = 8.0
Promedio final = 8.0 * 0.70 + 10.0 * 0.30 = 8.6
```

---

## Validaciones incluidas

- Login con rol.
- Restricción de acceso por tipo de usuario.
- Validación de campos vacíos.
- Validación de calificaciones entre 0 y 10.
- Validación de porcentajes de evaluación igual a 100%.
- Manejo de errores mediante alertas.
- Prevención de cierres inesperados por datos inválidos.

---

## Entregables incluidos

- Código fuente completo.
- Proyecto Maven ejecutable.
- Interfaz JavaFX funcional.
- Persistencia en XML.
- Documentación por fases.
- Modelo de datos.
- Diagramas.
- Manual de usuario.
- Explicación de arquitectura.
- Explicación de patrones de diseño.
- Guía de defensa individual.
- Casos de prueba.

---

## Nota académica

Este proyecto fue desarrollado con finalidad académica. Su objetivo es demostrar la aplicación de Programación Orientada a Objetos, JavaFX, MVC, patrones de diseño, persistencia y validaciones dentro de un sistema universitario de administración de calificaciones.
