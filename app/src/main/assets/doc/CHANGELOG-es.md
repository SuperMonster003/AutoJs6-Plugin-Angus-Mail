******

### Historial de versiones

******

# v1.0.0

###### 2026/09/18

* `Aviso` Vista previa de desarrollo P0: esqueleto del repositorio, nucleo de correo con pruebas en servidor local e identidad del plugin para el centro de plugins de AutoJs6. El contrato Binder, la API de script y la pagina de ajustes siguen las fases de ROADMAP.md.
* `Función` Identidad del plugin `angus-mail` (motor `mail`) con el servicio INFO, la Wake Activity y el esqueleto del servicio `org.autojs.plugin.MAIL` para el descubrimiento por el host
* `Función` Nucleo de correo sobre Eclipse Angus Mail: propiedades de sesion IMAP / POP3 / SMTP con SSL o STARTTLS, autenticacion por contrasena y XOAUTH2, envio SMTP y listado de la bandeja de entrada IMAP, verificados en un servidor GreenMail local
* `Función` README, instrucciones del centro de plugins y registro de cambios en 10 idiomas
* `Dependencia` Eclipse Angus Mail 2.0.5 (`org.eclipse.angus:jakarta.mail`) con Angus Activation 2.0.3 y Jakarta Activation API 2.1.4
* `Dependencia` Se agrega GreenMail 2.1.13 para las pruebas JVM del nucleo de correo (solo ambito de pruebas)
* `Dependencia` Se agrega `common-plugin-api.aar` (modulo de AutoJs6 `plugin-api/common-plugin-api`, build del host 6.8.0 / 5281, MPL 2.0) como contrato compartido de plugins, con hash bloqueado en `locks/host-api-aars.lock`
