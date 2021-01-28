# README #

### API Ventas Restaurante - Prueba Técnica Transbank ###


### Resumen ###

Este proyecto contiene 3 endpoints que simulan la gestion de ventas de un restaurante:
1. Crea una venta de uno o varios productos del restaurante POST http://localhost:8080/api/sales
2. Devuelve todas las ventas realizadas del día actual GET http://localhost:8080/api/sales
3. Envía una cantidad de mensajes a través de una cola que será la cantidad de veces que se llama al endpoint que devuelve la lista de ventas del día, para poder simular un endpoint con carga excesiva
   GET http://localhost:8080/api/sales/{quantity}
   
 Para acceder por primera vez a cualquiera de estos endpoint se debe acceder a través de un endpoint de login. usuario: user password:123456

### Explicación del modelo ###

Sale: Contiene el id de la venta y una lista de objetos ProductSaleDetail y el cliente.
ProductSaleDetail: Contiene un producto y la cantidad a vender de ese producto, esto quiere decir que para registrar una venta puedo seleccionar varios productos y por cada producto colocar la cantidad vendida
Product: Contiene el id del producto, nombre, precio y descripción
Customer: Contiene la información básica de un cliente como: identificación, nombre, número de teléfono y email
User: Contiene la información de los usuarios del restaurante, id, usuario, password, si está activo o no, y roles. Aunque no se están usando todos esos campos, de esta forma cuando la aplicación tenga más funcionalidades
      se puede integrar una seguridad más completa.
      
### Notas ###

1. El código del proyecto está escrito en inglés, sin embargo toda su documentación está en español, ya que la descripción de la prueba fue entregada en español.

### Version ###

1.0

# ¿Cómo configurar el proyecto? #


1. Ingrese a través de una cuenta de Github
2. Clone el proyecto restaurante
3. Importelo como un proyecto de maven desde un IDE

### Dependencias ###

- Java 8

### Configuración de la base de datos ###

- Mysql -> crear un schema restaurant puerto 3306

## Notas ##
El proyecto ya tiene un archivo que cargará una data inicial en su base de datos

### ¿Cómo probarlo? ###

- Click derecho en el proyecto
- Run as -> Java Application
- Desde su navegador ingrese a la dirección: http://localhost:8080/swagger-ui.html
- Ingrese usuario y contraseña: user : 123456
- Ingrese al endpoint POST /api/sales
- Ingrese la información para registrar una venta. (Puede guiarse en la documentación donde está detalladamente cada campo que debe ingresar).
      - Los productos con los que se carga la aplicación inicialmente son:
      - 1, sopa, 10000
      - 2, bandeja, 20000
      - 3, helado, 5000
      - 4, jugo, 5000 (id, name, price)
      - Si lo desea una vez esté arriba la aplicación, puede ingresar más productos de forma manual en su base de datos.
- Repita el paso anterior, pero modifique la fecha a una día anterior a la fecha actual. (Puede repetir estos dos últimos pasos las veces que quiera)
- Ingrese al endpoint GET /api/sales y ejecutelo
- Verifique que la lista de ventas retornadas correspondan unicamente a las que tienen fecha del día actual
- Ingrese al endpoint GET /api/sales/{quantity} 
- Ingrese en el parametro quantity la cantidad de veces que quiere que se ejequte el endpoint que obtiene la lista de ventas del día actual
- Ejecute el endpoint y revise la consola en su IDE, verificando que devuelva la lista de ventas del día actual la cantidad de veces que usted ingresó.

### Pruebas Unitarias ###
- Desde su IDE, ingrese a la carpeta src/test/java
- Click derecho en SalesServiceTest -> Run as JUnit Test
- Verifique que todas las pruebas se ejecuten de manera satisfactoria.
- Click derecho en SalesControlerTest -> Run as JUnit Test
- Verifique que todas las pruebas se ejecuten de manera satisfactoria.

## Notas ##
- Puede verificar que las pruebas unitarias verifican el comportamiento correcto del servicio y del controlador, así como algunas validaciones, para lo cual se aplicó un manejo de excepciones personalizadas.
- Las pruebas unitarias usan la base de datos embebida h2, por lo tanto no afecta la información de la base de datos

### Contacto ###

- Si tiene alguna pregunta o inquietud puede contactarme a spalacio@nisum.com
