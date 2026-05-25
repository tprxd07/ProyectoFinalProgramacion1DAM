# CRANK
## Tienda virtual

## 🚀 1. Funcionamiento General de la Aplicación

La aplicación gestiona un entorno transaccional cerrado donde conviven dos roles fundamentales tras el control de acceso y autenticación:

### 👤 Rol: Cliente / Usuario Estándar
* **Exploración Avanzada:** El usuario accede a un catálogo visual dinámico donde puede examinar las aplicaciones, ver sus fichas de detalles, precios, número de descargas y la media de sus valoraciones cuantitativas.
* **Filtrado Predictivo (API Stream & Lambdas):** Permite buscar software instantáneamente por conciencias en el nombre o mediante categorizaciones restrictivas (`Salud`, `Finanzas`, `MMO`, `Shooter`, `Indie`, `Ocio`, `Aventura`).
* **Adquisición de Software Transaccional:** El usuario dispone de un monedero virtual con saldo. Al pulsar en *"Adquirir"*, el sistema valida los fondos, realiza el cobro atómico, asocia permanentemente la licencia a su biblioteca (`adquiere`), audita el pago histórico (`paga`) e incrementa el contador global de descargas.
* **Feedback Social:** El cliente puede redactar reseñas con comentarios de texto y puntuaciones numéricas (`smallint`) asociadas de forma única a los productos adquiridos.

### 👨‍💻 Rol: Administrador / Desarrollador
* **Panel de Control CRUD:** Acceso prioritario a formularios avanzados encargados de dar de alta nuevas aplicaciones especializándolas en `Videojuego` (definiendo si es multijugador o no) o `Utilidad`.
* **Mantenimiento Indexado:** Capacidad para insertar nuevas entidades de empresas desarrolladoras asociándoles su país de origen para indexar la procedencia del software distribuido.

---

## 🏗️ 2. Arquitectura y Estructura del Proyecto

El desarrollo se fundamenta estrictamente en el patrón arquitectónico **MVC (Modelo-Vista-Controlador)** y en una separación en capas de la lógica empresarial mediante el patrón **DAO (Data Access Object)**. Esto asegura un bajo acoplamiento y una alta cohesión del código.

La estructura de directorios del proyecto estructurado con **Maven** se distribuye de la siguiente manera:

```text
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java
│   │   │   └── 📂 com.playstore
│   │   │       ├── 📄 Main.java                # Punto de entrada de la aplicación (Lanza JavaFX)
│   │   │       │
│   │   │       ├── 📂 model                    # Capa de Entidades Puras (POJOs)
│   │   │       │   ├── 📄 Aplicacion.java      # Clase base / superclase abstracta (RA 7)
│   │   │       │   ├── 📄 Videojuego.java      # Especialización / Herencia (Hijo 1)
│   │   │       │   ├── 📄 Utilidades.java      # Especialización / Herencia (Hijo 2)
│   │   │       │   ├── 📄 Usuario.java         # Datos de usuario, credenciales y saldo
│   │   │       │   ├── 📄 Desarrollador.java    # Datos de empresas creadoras de software
│   │   │       │   └── 📄 Resenia.java        # Entidad de opiniones e histórico cuantitativo
│   │   │       │
│   │   │       ├── 📂 controller               # Capa de Controladores (JavaFX Events)
│   │   │       │   ├── 📄 LoginController.java # Control de sesiones y autenticación local
│   │   │       │   ├── 📄 TiendaController.java# Gestión del catálogo principal y Streams
│   │   │       │   └── 📄 AdminController.java # Control y procesamiento del panel CRUD
│   │   │       │
│   │   │       └── 📂 dao                      # Capa de Persistencia e Integridad (JDBC)
│   │   │           ├── 📄 ConexionBD.java      # Singleton para el ciclo de vida de la conexión
│   │   │           ├── 📄 UsuarioDAO.java      # Operaciones CRUD e incremento de saldos
│   │   │           ├── 📄 AplicacionDAO.java   # Lecturas optimizadas y Lazy Loading
│   │   │           └── 📄 BibliotecaDAO.java   # Transacciones complejas (Commit/Rollback)
│   │   │
│   │   └── 📂 resources                        # Ficheros de recursos del sistema
│   │       ├── 📂 fxml                         # Diseños de pantallas XML (JavaFX Scene Builder)
│   │       │   ├── 📄 login.fxml
│   │       │   ├── 📄 tienda_principal.fxml
│   │       │   └── 📄 panel_admin.fxml
│   │       ├── 📂 css                          # Hojas de estilo para la UI
│   │       │   └── 📄 styles.css
│   │       └── 📂 images                       # Almacenamiento local de assets e iconos
│   │           └── 📄 placeholder.png
│   │
│   └── 📂 test                                 # Capa de pruebas unitarias locales
│
├── 📄 pom.xml                                  # Configuración de dependencias de Maven
└── 📄 README.md                                # Documentación principal del repositorio
