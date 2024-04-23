 # Descripción

Esta aplicación es una plataforma de gestión de usuarios que utiliza Spring Security y JWT (JSON Web Tokens) para garantizar la seguridad y la autenticación de los usuarios. Permite la gestión de usuarios, roles y permisos, proporcionando una interfaz de usuario intuitiva y segura para administrar estas entidades.

# Funcionalidades principales

-   **Autenticación segura:** Utiliza Spring Security para gestionar la autenticación de los usuarios, asegurando que solo los usuarios autorizados puedan acceder a las diferentes funcionalidades de la aplicación.
    
-   **Autorización basada en roles y permisos:** Implementa un sistema de roles y permisos para controlar el acceso a diferentes partes de la aplicación. Los usuarios pueden tener uno o varios roles, y cada rol puede tener uno o varios permisos asociados.
    
-   **Generación y gestión de JWT:** Utiliza JWT para generar tokens de acceso seguro después de que un usuario se autentica correctamente. Estos tokens se utilizan para autorizar las solicitudes del usuario y acceder a recursos protegidos de la aplicación.

    **Persistencia de datos con Spring Data JPA:** Utiliza Spring Data JPA para interactuar con una base de datos relacional, permitiendo que los usuarios y sus datos se almacenen y recuperen de manera eficiente.

# Tecnologías utilizadas

-   **Spring Boot:** Framework de desarrollo de aplicaciones Java que proporciona un entorno preconfigurado para el desarrollo de aplicaciones basadas en Spring.
    
-   **Spring Security:** Módulo de Spring que proporciona autenticación y autorización para aplicaciones Java.
    
-   **JWT (JSON Web Tokens):** Un estándar abierto (RFC 7519) que define un formato compacto y autónomo para la transmisión de información de forma segura entre partes como un objeto JSON.
 
-   **Spring Data JPA:** Parte del proyecto Spring que proporciona una abstracción de alto nivel para interactuar con bases de datos relacionales utilizando el estándar de mapeo objeto-relacional (ORM) de Java.
    
-   **Validator:** Mecanismo de validación de datos utilizado para garantizar la integridad y la coherencia de los datos ingresados por los usuarios.
  
-   **Postman:** Herramienta de colaboración para el desarrollo de API utilizada para interactuar y probar servicios web.
    
-   **Visual Studio Code:** Editor de código fuente desarrollado por Microsoft para Windows, Linux y macOS, utilizado para escribir y depurar el código de la aplicación.

# Requisitos previos

-   Java JDK 21.0.1 o superior
-   Base de datos MySQL
-   Visual Studio Code (o cualquier otro editor de código compatible con Java)

# Configuración

1.  Clona el repositorio desde GitHub: `git clone https://github.com/sebasrb08/Spring-Security.git`
2.  Importa el proyecto en tu IDE preferido.
3.  Configura la conexión a la base de datos en el archivo `application.properties`.
4.  Ejecuta la aplicación utilizando Maven o tu IDE.

# Uso

Puedes utilizar Postman para interactuar con la API de esta aplicación de la siguiente manera:

1.  **Registrar un usuario:** Envía una solicitud POST a la ruta `/auth/sign-in` con un objeto JSON que contenga los datos del nuevo usuario para registrarlo.
    
2.  **Iniciar sesión:** Envía una solicitud POST a la ruta `/auth/log-in` con las credenciales de inicio de sesión en el cuerpo de la solicitud en formato JSON. Esto generará un token JWT que deberás usar para las solicitudes posteriores.
    
3.  **Acceder a rutas protegidas:**
    
    -   **GET "/method/get":** Todos los roles pueden acceder a esta ruta para ver los accesos.
    -   **POST "/method/post":** Solo los roles ADMIN y DEVELOPER pueden acceder a esta ruta.
    -   **PUT "/method/put":** Ningun rol puede acceder a esta ruta para realizar una actualización.
    -   **DELETE "/method/delete":** Ningun rol puede acceder a esta ruta para realizar una actualización.
    -   **PATCH "/method/patch":** Solo los roles DEVELOPER pueden acceder a esta ruta.

Recuerda incluir el token JWT en el encabezado de autorización de las solicitudes para autenticar y autorizar correctamente.

Si usas Postman usar Bearer token y enviar el token para obtener la autorización

# Contribución

¡Las contribuciones son bienvenidas! Si tienes alguna idea para mejorar esta aplicación, no dudes en abrir un issue o enviar una pull request.

