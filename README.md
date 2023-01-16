<<<<<<< HEAD
<<<<<<< Updated upstream
# Proyecto Final PAMN
=======
=======
>>>>>>> main
# **Proyecto Final PAMN**

Pretendemos hacer un aplicación "simple", pero que pueda, de alguna forma, ser útil. Decidimos realizar una aplicación con características informativas. 

Habrán 3 tipos de usuario:
- No registrados
- Registrados
- Delegados / Administradores

Los delegados / administradores serán los únicos que podrán subir publicaciones informativas, con foto y texto, o solo texto.  

Cualquier usuario podrá ver las publicaciones.   
Siendo un usuario registrado podrá _hacer comentarios_ en las publicaciones y _guardarlas en favoritos_.  
Siendo un delegado / administrador podrá _editar_ y _eliminar_ las publicaciones y podrá eliminar cualquier comentario.  

Haciendo referencia a lo dado en clase, intentaremos seguir las fases Devops, a la par que utilizaremos Metodologías de Desarrollo Ágil durante el desarrollo de la práctica.

---

## **Fases del Desarrollo**

### **Fase 1: Análisis del proyecto** 📝
Mediante el uso de bocetos en digital propusimos ideas, tanto de de las distintas funcionalidades, como de la estética de la interfaz que podría llegar a tener la aplicación.  
A su vez, usamos [trello](https://trello.com/es) para planear el proyecto. Con respecto a la Metodología de Desarrollo Ágil, hemos decidido adaptarnos, ya que tenemos muchos trabajos, de forma que:
- Se irán proponiendo tareas durante la primera y segunda semana
- Nos asignaremos las tareas propuestas
- Cada jueves, en clase de prácticas, tendremos una pequeña reunión, diciendo los distintos problemas que hemos podido encontrar
- A su vez, todos los días iremos hablando poco a poco del trabajo  

Trello -> [Tablero de Trello ](https://trello.com/invite/b/5WkKyg91/ATTI7843f8fa3a9e5219bac81f54b5b7cdd45F76642F/pamndelega)

Bocetos -> PDF ubicado en la carpeta **[Bocetos e Ideas]** (Se subirá en algún momento y se irá modificando a necesidad)

---

### **Fase 2: Diseño de la aplicación** 🖌
Dado que pretendemos hacer una aplicación para la Delegación de Estudiantes de la Escuela de Ingeniería Informática, se tomaron las fotos de la [página oficial](https://www.ulpgc.es/identidad-corporativa/marca-grafica-ulpgc), para poder usarlas de forma correcta.

También se tomaron ideas de la pagina de registro de MiULPGC  
![MiULPGC](docs/Bocetos%20e%20Ideas/Inicio_MiULPGC2.png)

Se sacaron los colore básicos de la ULPGC:  
![Colores ULPGC](docs/Bocetos%20e%20Ideas/Colores.png)

Se cogió un wireframe de un móvil con android -> 
[Samsung Galaxy Wireframe Mockup](https://www.figma.com/file/YxhF8ALI0VuQqARKTiDSUe/samsung-galaxy-wireframe-mockup-(Community)?node-id=0%3A1)



Figma -> [Esquema en Figma](https://www.figma.com/file/G9zPa1o3azyYjJuFsx6RSS/COSA-PAMN?node-id=0%3A1)
Se ha hecho unos de algunos plugins, como son:
- [Inconify](https://www.figma.com/community/plugin/735098390272716381), para poder añadir iconos vectoriales de forma más sencilla.
- [Lore](https://www.figma.com/community/plugin/984557085378252054), para poder poner un texto de relleno.
- [Wireframe](https://www.figma.com/community/plugin/742764242781786818), para la idea de algunos componentes de la aplicación, como puede ser la foto por defecto.



---

### **Fase 3: Documentación y estudio** 📚
Se irán colocando los Codelabs más interesantes para el desarrollo del trabajo, así como vídeos o páginas que nos sirvan de ayuda.
- [www.codigofacilito.com](https://codigofacilito.com/articulos/articulo_18_10_2019_18_25_35) -> Puede ayudar a la hora de hacer el login 
- [Creación de layouts](https://youtu.be/5jXy2S-qdVQ?t=2729) -> Puede ayudar a la creación de las distintas publicaciones, comentarios y zonas donde tengamos que mostrar información.
<<<<<<< HEAD
- [Creación de diálogos](https://developer.android.com/guide/topics/ui/dialogs?hl=es-419)
=======
- []() ->
>>>>>>> main
- []() ->

---

### **Fase 4: Desarrollo** 📐
Para el desarrollo de la aplicación se ha tratado de aplicar la “Guía de Arquitectura”
de Android Studio. Con respecto a la arquitectura tratamos de hacer lo más
parecido a MVC (Model View Controller), teniendo modelos como pueden ser los
usuarios o las publicaciones, las vistas de la propia aplicación e interfaces como una
especie de control.

---

### **Fase 5: Pruebas y despliegue** 💻
Se irán haciendo pruebas en el emulador que nos aporta Android Studio, y a terminar la app se descargará en nuestro dispositivo físico para ver su correcto uso.

---

## **Temporización**
Durante las 2 primeras semanas fuimos alternando entre proponer ideas para el Trello e
ir diseñando la aplicación:
	● Dentro de Trello hemos decidio tener 10 calles:
		○ Historias épicas: Historias demasiado grandes y costosas, pero que si a futuro
seguimos con la aplicación podrían llegar a ser viables.
		○ Figma: Historias de usuario de creación de mockups en Figma.
		○ Historia de usuario(product backlog): Historias de usuarios reales y relacionadas
con la programación de la aplicación.
		○ En proceso: Historias que se están desarrollando en el momento actual.
		○ Estancado: Historias que por diversos motivos no pueden continuar o no se pueden
dar por terminadas.
		○ Pendiente de revisión: Historias ya acabadas, pendientes de que la otra parte del
grupo o algún usuario externo compruebe su correcto funcionamiento (en caso de
cambio se pondría otra vez en Historia de usuario o en En proceso).
		○ Actividades terminadas: Actividades/Vistas de la aplicación terminadas 100%.
		○ Mockups terminados: Mockups diseñados en Figma que se han aprobado a nivel
estético y únicamente como referencia.
		○ Funciones terminadas: Funcionalidades acabadas de forma que son seguras para
proseguir con la siguiente actividad.
Decidimos no agregar horas a las historias de usuarios debido a que estamos empleando
un lenguaje nuevo y no sabemos con aproximación el tiempo que nos llevaría. Lo que
hicimos fue agregar etiquetas de prioridad a aquellas historias que eran totalmente
indispensables para la primera versión de la aplicación.
Una vez pasaron esas 2 semanas de organización, empezamos a usar Android Studio para
desarrollar las primeras actividades y layouts (Iniciar sesión y registro) y para conectar
con la Cloud Firestore, de forma correcta y sin errores.
Cada jueves, en la clase de práctica, comentamos los problemas que fuimos encontrando
durante la semana de forma que ese mismo día pudieran estar resueltos para poder
proseguir con la aplicación.
Además, diariamente se hablaba un poco de lo que habíamos hecho y los avances que
íbamos teniendo.
A 5 semanas de la entrega del trabajo nos encontramos con que no éramos capaces de
mostrar los datos recogidos de Firestore, como se explicó antes, las llamadas son
asíncronas y el hilo principal se ejecutaba antes de recibir los datos, lo que nos llevó a los
dos integrantes tratar de resolver este problema que nos llevó su tiempo.
Una vez esto fue solventado, solo quedaba crear un par de vistas y recoger los 2 tipos de
datos para poder usarlos en toda la aplicación.


---

## **Herramientas utilizadas**
- [GitKraken](https://www.gitkraken.com/) -> Para el control de versiones.
- [Trello](https://trello.com/) -> Monitorizar las tareas y el trabajo que se va realizando.  
- [Figma](https://www.figma.com/) -> Bocetos y diseño de la aplicación.  
- [Android Studio](https://developer.android.com/studio) -> Para el desarrollor de la Aplicación.  
- [Firebase]() ->  Para cosas de Login y BBDD.  
- []() ->   
- []() ->   
- []() ->   
- []() ->   


=======


---

## Planteamiento de la Base de Datos (Abierto a cambios)##
Como se ha planteado se utilizara Firebase en concreto el recurso CloudFirestore para almacenar y sincronizar los datos de la app.
El planteamiento para la primera versión sería tener dos colecciones -> "users"y "news".

La colección "users" almacenará todos los usuarios y estos tendrán propiedades para poder identificarlos.
	Propiedades de los "users" (El nombre de los documentos dentro de esta colección será el nombre de los propios usuarios):
		-id = number
			La id será dada por el programa, de forma simple se le agrega un número y al siguiente usuario se la suceción del anterior.
		-rol = number
			Habrán 3 tipos de rol: "1" -> Delegados, "2" -> Usuarios, "3" -> Invitados.
		-password = String
			Contraseña dada por el usuario, por ahora no esta incriptada, es una simple String, ¡Habrá que cambiarlo!
		-description = String
			Descripción simple de quien es el usuario.
		-movil = number
			Móvil del usuario, se usara como vía de comunicación.
		-email = String
			Correo institucional del usuario, se usará para el login y como via de comunicación.
		-discord = String
			Discord del usuario, se usará como vía de comunicación



La colección "news" almacenará todas las noticias y estas tendrán propiedades para poder identificarlas.
	Propiedades de las "news" (El nombre de los documentos dentro de esta colección será el titulos de las propias news):
		-id = number
			La id de la publicación para poder identificarla, vendra dada por un numero que ira cambiando secuencialemente.
		-image = String
			Será una imagen guardada en CloudFirestore
		-description = String
			Descripción de la publicación
		-users = List of users
			Lista de Usuarios que habrán agregado a favoritos la propia publicacíon.
>>>>>>> main
