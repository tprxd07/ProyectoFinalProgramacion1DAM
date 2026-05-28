# CRANK
## Tienda virtual

## 🚀 1. Funcionamiento General de la Aplicación

La aplicación gestiona un entorno transaccional cerrado donde conviven dos roles fundamentales tras el control de acceso y autenticación:

### 👤 Usuario
* **Exploración Avanzada:** El usuario accede a un catálogo visual dinámico donde puede examinar las aplicaciones, ver sus fichas de detalles, precios, número de descargas y la media de sus valoraciones cuantitativas.
* **Filtrado Predictivo (API Stream & Lambdas):** Permite buscar software instantáneamente por conciencias en el nombre o mediante categorizaciones restrictivas (`Salud`, `Finanzas`, `MMO`, `Shooter`, `Indie`, `Ocio`, `Aventura`).
* **Adquisición de Software Transaccional:** El usuario dispone de un monedero virtual con saldo. Al pulsar en *"Adquirir"*, el sistema valida los fondos, realiza el cobro atómico, asocia permanentemente la licencia a su biblioteca (`adquiere`), audita el pago histórico (`paga`) e incrementa el contador global de descargas.
* **Feedback Social:** El cliente puede redactar reseñas con comentarios de texto y puntuaciones numéricas (`smallint`) asociadas de forma única a los productos adquiridos.
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
│   │   │   └── 📂 org.example.proyectofinalprogramacion1dam
│   │   │       │
│   │   │       ├── 📂 controller                   # Capa de Controladores (JavaFX Events)
│   │   │       │   ├── 📄 LoginController.java
│   │   │       │   └── 📄 TiendaPrincipalController.java
│   │   │       ├── 📂 modelDAO                     # Capa de conexión a la base de datos
│   │   │       │   ├── 📄 ConnectionBD.java
│   │   │       │   ├── 📄 ConnectionPropierties.java
│   │   │       │   └── 📄 XMLManager.java
│   │   │       │
│   │   │       ├── 📂 model                        # Capa de Entidades Puras (POJOs)
│   │   │       │   ├── 📄 Aplicacion.java
│   │   │       │   ├── 📄 Usuario.java   
│   │   │       │   ├── 📄 Desarrollador.java
│   │   │       │   └── 📄 Resenia.java
│   │   │       │
│   │   │       ├──  📂 modelDAO                    # Capa de Persistencia e Integridad (JDBC)
│   │   │       │    ├── 📄 ConexionBD.java
│   │   │       │    ├── 📄 UsuarioDAO.java
│   │   │       │    ├── 📄 AplicacionDAO.java
│   │   │       │    └── 📄 BibliotecaDAO.java
│   │   │       │
│   │   │       ├── 📂 utils                        # Capa de clases con metodos auxiliares
│   │   │       │   ├── 📄 Alerta.java
│   │   │       │   ├── 📄 SceneManager.java   
│   │   │       │   ├── 📄 Sesion.java
│   │   │       │   └── 📄 Util.java
│   │   │       │
│   │   │       └── 📂 view                         # Capa con la aplicacion principal
│   │   │       │   └── 📄 MainApp.java
│   │   │       └──📄 Launcher.java
│   │   │
│   │   └── 📂 resources                            # Ficheros de recursos del sistema
│   │   │   └── 📂 org.example.proyectofinalprogramacion1dam                       
|   |   |       ├── 📂 fxml                         # Diseños de pantallas XML (JavaFX Scene Builder)
│   │   │       │    ├── 📄 login.fxml
│   │   │       │    ├── 📄 tienda_principal.fxml
│   │   │       ├── 📂 css                          # Hojas de estilo para la UI
│   │   │       │   └── 📄 styles.css
│   │   │       ├── 📂 sounds                       # Sonido de descargas
│   │   │       │   └── 📄 descarga.mp3
│   │   │       └── 📂 images                       # Almacenamiento local de assets e iconos
│   │                └── 📄 placeholder.png
│   │
├── 📄 pom.xml                                  # Configuración de dependencias de Maven
└── 📄 README.md                                # Documentación principal del repositorio
