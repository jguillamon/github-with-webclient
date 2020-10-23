# Integracion github con webclient
Microservicio que conta de un servicio para integrarse con el login de aplicacion de github mediante oauht
#### Tecnologías
Java 11

Spring Boot 2.3.4.RELEASE

#### Configuración
Editar el fichero *application.yml* las entradas.
> **github.clientId**: clientId proporcionado por github para tu aplicacíon. Información para crear la aplicación: https://github.com/settings/applications/new
>
> **github.clientSecret**: clientSecret proporcionado por github para tu aplicacíon. Información para crear la aplicación: https://github.com/settings/applications/new
>

#### Ejecución
Para probar el proceso hay que levantar primero el servidor basaso en spring-boot y asi tendremops accesible el servicio 
http://localhost:8080/user-info-github (si no cambiaste la configuracion del puerto). Ahora hay que configurar en la gestion de tu configuracion de github y ponerle como
*Authorization callback URL* la url de este servicio http://localhost:8080/user-info-github 

Por último acceder a https://github.com/login/oauth/authorize?client_id={clientId} con el clientId de tu aplicación github,
si estas logado te preguntará para conceder permisos de esta aplicación a tu usuario, si no estas logado antes tendras que logarte.

Si todo el proceso va bien, github permitirá el paso correctamente y se realizará una redireccion a tu servicio */user-info-github*, que este por dentro 
autenticará para obtener un access_token valido y con este access_token realizará una petición autenticada para obtener los datos del usuario actualmente logado
devolviendolos en la salida de la petición como json.
