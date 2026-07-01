# TP Integrador DESI — Sistema de Gestión de Alquileres

**Grupo:** Diaz · Nieva · Garces · Coppola  
**Materia:** Desarrollo de Sistemas de Información — UTN

---

## Requisitos previos

- Java 17+
- MySQL corriendo en `localhost:3306`
- Editá el `application.properties` con tu usuario y contraseña de mysql: 
```
    spring.datasource.username=<tu-usuario>
    spring.datasource.password=<tu-contraseña>
```

## Cómo correr el proyecto

```bash
./mvnw spring-boot:run
```

Al iniciar, el sistema:
1. Crea la base de datos `tp_alquileres` si no existe.
2. Crea o actualiza las tablas automáticamente (Hibernate DDL).
3. Inserta los datos de prueba (propietarios e inquilinos) si no existen.

Abrir en el browser: **http://localhost:8080/home**

---

## Estructura del proyecto

```
src/main/java/tuti/desi/
├── entity/          Entidades JPA (Propietario, Inquilino, Propiedad, Contrato)
├── dto/             Objetos de transferencia de datos
├── enums/           EstadoPropiedad, TipoPropiedad, EstadoContrato
├── repository/      Interfaces Spring Data JPA
├── service/         Interfaces de servicio
│   └── impl/        Implementaciones con lógica de negocio
└── controller/      Controllers Spring MVC

src/main/resources/
├── templates/
│   ├── home.html                     Pantalla de inicio
│   ├── fragments/layout.html         Layout base con navbar y Bootstrap 5
│   ├── propiedades/lista.html        Lista de propiedades
│   ├── propiedades/form.html         Alta / modificación de propiedad
│   ├── contratos/lista.html          (en construcción)
│   └── contratos/form.html           (en construcción)
├── data.sql                          Datos de prueba (se ejecuta automáticamente)
└── application.properties
```

---
