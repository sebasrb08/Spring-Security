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
    
-   **Base de datos:** La aplicación utiliza una base de datos  MySQL  para almacenar y gestionar la información de usuarios, roles y permisos.
    

# Requisitos previos

-   Java JDK 21.0.1 o superior
-   Maven 3.2 o superior
-   Base de datos MySQL
-   Visual Studio Code (o cualquier otro editor de código compatible con Java)

# Configuración

1.  Clona el repositorio desde GitHub: `git clone https://github.com/tu-usuario/tu-repositorio.git`
2.  Importa el proyecto en tu IDE preferido.
3.  Configura la conexión a la base de datos en el archivo `application.properties` o `application.yml`.
4.  Ejecuta la aplicación utilizando Maven o tu IDE.

# Uso

Una vez que la aplicación esté en funcionamiento, puedes acceder a la interfaz de usuario para gestionar usuarios, roles y permisos. Utiliza las funcionalidades proporcionadas por la aplicación para agregar, editar o eliminar usuarios, asignar roles a usuarios y definir los permisos asociados a cada rol.

# Contribución

¡Las contribuciones son bienvenidas! Si tienes alguna idea para mejorar esta aplicación, no dudes en abrir un issue o enviar una pull request.

# Licencia

Este proyecto está licenciado bajo la Licencia MIT.
