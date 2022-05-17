# DESCRIPCION DE LOS ATROBUTOS DE LOS RECURSOS QUE TENEMOS EN LA API:

Market:
	- id: String, id unico asignado automaticamente.

	- name: String, nombre del mercado.

	- description: String, descripcion del mercado.

	- products: List<Product>, todos los productos del mercado, la eliminacion del mercado implica la eliminacion de los productos.

	- orders: List<Order>, todos los pedidos del mercado, la eliminacion del mercado implica la eliminacion de los pedidos.

Order:
	- id: String, id unico asignado automaticamente.

	- idMarket: String, id unico asignado al mercado del pedido.

	- dateStart: LocalDate, fecha de la creacion del pedido, se le asigna automaticamente al crear el pedido.

	- dateDelivery: LocalDate, fecha de la entrega del pedido, se le asigna automaticamente mediante una ruta especifica get (orders/delivered/{orderId}) que hace update de este atributo a la fecha de cuando se llama a la ruta.

	- address: String, lugar de entrega del pedido.

	- shippingCosts: Double, coste de la entrega del pedido.

	- user: User, usuario que ha creado el pedido. Para crear el pedido con la ruta especifica post (orders) el usario que se añada el campo user de la nueva order tiene que existir ya y su token ser el que le mandas en el QueryParam token. **Para el usuario he pensado (JOAQUIN) que lo mejor sera en el apartado "user", de los parametros del Json que se manda para crear order, que tenga solo el atributo idUser entonces se buscaese user en los usuarios ya existentes y se hace setUser(usuario encontrado en la memoria) pero hay que ver como se comporta**.

	- products: List<Product>, todos los productos del pedido, la eliminacion del pedido no implica que se eliminen los productos de este pedido.

Product:
	- id: String, id unico asignado automaticamente.

	- idMarket: String, id unico del market del producto.

	- name: String, nombre producto.

	- price: Double, precio producto.

	- rating: Integer, valoracion producto entre 1-5.

	- quantity: Integer, cantidad almacenada del producto.

	- expirationDate: LocalDate, fecha caducidad del producto.

	- type: ProductType, enumerado ProductType con los valores {LEISURE, FOOD, CLOTHES,  HEALTH, SPORT}

User:
	- id: String, id unico asignado automaticamente.

	- name: String, nombre del user.

	- email: String, email del user.

	- password: String, contraseña del user.

	- token: String, cadena de caracteres que se genera automaticamente al crearse cualquier usuario.

	- address: String, lugar de residencia del usuario. (Idea para implementar en el futuro que cuando se haga crea un pedido el atributo address sea por default el address del user que la creo sino indican - uno especifico en el Json).

# Implementación de una API REST 

Proyecto de Integracion de la asignutara AISS.

La API REST estará formada por cuatro recursos que permitirán manipular grandes almacenes, pedidos a estos almacenes, los productos que almacenan y la informacion del usuario actual.

El contrato de servicios de los productos se detalla a continuación.

### Recurso Product ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET |  /products | Devuelve todos los productos de la aplicación. •	Es posible ordenar los productos con el parámetro de query “order”, que acepta los valores “price”, “-price”, “quantity”, “-quantity”, “rating”, “-rating”. •	También es posible filtrar los productos devueltas con el parámetro de query “q”, que devuelve aquellos productos cuya categoria, nombre contengan la cadena enviada (ignorando mayúsculas y minúsculas), o los valores “price”, “-price”, “rating”, “-rating”, “quantity”, “-quantity” que devuelve los productos que tengan el precio o la valoracion mayor o menor que el parametro query "qAux". **Si el parametro query "q" lleva alguno de los ultimos valores el parametro de query "qAux" tiene que llevar el entero sobre el que quieras comparar** • Tambien se puede filtrar los productos por el parametro query "queryExpiration" que es un Boolean que si toma valor true devuelve los productos que han caducado y si es false los que no han caducado **se puede usar en combinacion con los filtros anteriores** •	Es posible establecer un limite en el numero de productos devueltos mediante el parametro de query "limit" y el paraemtro de query "offset" para desplazar el comienza de los productos devueltos |
| GET | /products/{productId}  |  Devuelve el producto con i:productId. Si el producto no existe devuelve un “404 Not Found”. |
| POST | /products | Añade un nuevo producto cuyos datos se pasan en el cuerpo de la petición en formato JSON (no se debe pasar id, se genera automáticamente). Si el nombre del producto no es válido (null o vacío), si la fecha de caducidad no es valida (anterior a la fecha actual o vacío), si el precio o la cantidad son negativos devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido del producto. |
| PUT | /products  | Actualiza el producto cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id del producto). Si el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /products/{productId}  |  Elimina el producto con i:productId. Si el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.|

### Ejemplos Product ###

1. GET: /products
**Datos en formato JSON de salida**
```cpp
{
  "products": [
    	{
      "id": "p3",
      "name": "Macarrones",
      "price": "2.5",
      "rating": "4",
      "quantity": "1991",
      "expirationDate": "2023-05-18",
      "type": "FOOD"
    }
  ]
}
```
2. GET: /products/{productId}
**Datos en formato JSON de salida**
```cpp
{
	"id":"p3",
	"name":"Macarrones",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18",
	"type": "FOOD"
}
```
3. POST: /products
**Datos en formato JSON de entrada**
```cpp
{
	"name":"Macarrones ",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18",
	"type": "FOOD"
}
```
4. PUT: /products
**Datos en formato JSON de entrada**
```cpp
{
	"id":"p3",
	"name":"null",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18",
	"type": "null"
}
```
5. DELETE: /products/{productId}
**No es necesario JSON de entrada**

Cada **producto** tiene un identificador, nombre, precio, valoracion, cantidad, fecha de caducidad y categoria del producto . La representación JSON del recurso es:

```cpp
{
	"id":"p3",
	"name":"Macarrones",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18",
	"type": "FOOD"
}
```

### Recurso User ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET | /users | Ver todos los usuarios existentes. •	Es posible ordenar los usuarios por sus nombres con el parámetro de query "order", que solo acepta dos valores, "name" o "-name" que devuelve los usuarios ordenados en orden alfabetico o orden inverso.   •	Tambien se puede usar el parametro de query "limit" y "offset" para limitar el numero de usuarios mostrados y el comienzo de estos. |
| GET | /users/{userId} | Devuelve el usuario con i:userId. Si el usuario no existe devuelve un “404 Not Found”. |
| POST | /users | Añadir un nuevo usuario. Los datos del usuario se proporcionan en el cuerpo de la petición en formato JSON. Si el nombre, la contraseña o la dirección del usuario no son válidos (nulo o vacío), devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de el pedido. |
| PUT | /users | Actualiza el usuario cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id del usuario).  Si el usuario no existe, devuelve un “404 Not Found”. Solo se pueden actualizar el nombre, email, contraseña o la direccion. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /users/{userId} | Elimina el usuario con i:userId. Si el usuario no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| GET | /users/token/{userId}  | Devuelve el token del usuario con i:userId. Si el usuario no existe devuelve un “404 Not Found”. Para poder ser realizado necesita obligatoriamente dos parametros de query "name" y "password" que tienen que ser el nombre y contraseña del usuario con i:userId, en cualquier otro caso devuelve un error “400 Bad Request” |

### Ejemplos User ###

1. GET: /users
**Datos en formato JSON de salida**
```cpp
{
	"users": [
  	{
			"id": "u3",
			"name": "Maria",
			"email": "aa@aiss.com",
			"address": "baños, 33"
		}
  ]
}
```

2. GET: /users/{userId}
**Datos en formato JSON de salida**
```cpp
{
	"id": "u3",
	"name": "Maria",
	"email": "aa@aiss.com",
	"address": "baños, 33"
}
```

3. POST: /users
**Datos en formato JSON de entrada**
```cpp
{
	"name": "Maria",
	"email": "aa@aiss.com",
	"password": "secret",
	"address": "baños, 33"
}
```

4. PUT: /users
**Datos en formato JSON de entrada**
```cpp
{
	"id": "u3",
	"name": "Maria",
	"email": "aa@aiss.com",
	"password": "secret2",
	"address": "baños, 33"
}
```

5. DELETE: /users/{userId}
**No es necesario JSON de entrada**

6. GET: /users/token/{userId}
**Datos en formato JSON de salida**
```cpp
{
	"token": "WEaslkjflaJKSafdlDa3"
}
```

### Recurso Order ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET | /orders  | Ver todos los pedidos existentes. •	Es posible ordenar los pedidos por fecha con el parámetro de query “order”, que solo acepta dos valores, “dateStart” o “-dateStart” que devuelve los pedidos entregados o no entregados. •	También es posible filtrar los pedidos devueltas con dos parámetros de query: “order”, que solo acepta dos valores, “delivered” o “-delivered” que devuelve los pedidos entregados o no entregados.; “name”, que devuelve los pedidos cuyo nombre coincida exactamente con el valor del parámetro. |
| GET | /orders/{orderId} | Devuelve el pedido con i:orderId. Si el pedido no existe devuelve un “404 Not Found”. |
| POST | /orders | Añadir un nuevo pedido. Los datos de el pedido (nombre y descripción) se proporcionan en el cuerpo de la petición en formato JSON. Los productos del pedido no se pueden incluir aquí, para ello se debe usar  la operación POST específica para añadir una producto a una lista (a continuación). Si el nombre de el pedido no es válido (nulo o vacío), o se intenta crear una lista con canciones, devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de el pedido. |
| PUT | /orders | Actualiza el pedido cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id de el pedido).  Si el pedido no existe, devuelve un “404 Not Found”. Si se intenta actualizar las canciones de el pedido, devuelve un error “400 Bad Request”. Para actualizar las canciones se debe usar el recurso product mostrado previamente. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /orders/{orderId} | Elimina el pedido con i:orderId. Si el pedido no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| POST |  /orders/{orderId}/{productId} | Añade el producto con i:productId a el pedido con i:orderId. Si el pedido o el producto no existe, devuelve un “404 Not Found”. Si el producto ya está incluida en el pedido devuelve un “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de el pedido. |
| DELETE | /orders/{orderId}/{productId}  | Elimina el producto con i:productId de el pedido con i:orderId. Si el pedido o el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.|


Una **lista** tiene un _identificador, nombre, descripción y un conjunto de canciones_. La representación JSON de este recurso es:

```cpp
{
	"id":"p5",
	"name": "mercadona",
	"dateStart":"2023-05-18",
	"dateDelivery": "2023-05-20",
	"shippingCost":"5",
	"user": {
		"id": "u3",
		"name": "Maria",
		"email": "aa@aiss.com",
		"address": "baños, 33"
	},
	"products":[
		{
			"id":"p3",
			"name":"Macarrones",
			"price":"2.5",
			"rating":"4",
			"quantity":"1991",
			"expirationDate": "2023-05-18",
			"type": "FOOD"
		},
		{
			"id":"p4",
			"name":"Zapatos",
			"price":"30",
			"rating":"3",
			"quantity":"1991",
			"expirationDate": "2023-05-18",
			"type": "FOOD"
		}
		]
}

```

**Ejemplo de mercado individual JSON**
```cpp
`{
	"id": "m5",
	"name": "mercadona",
	"descripcion": "supermercado mercadona",
	"products": [
		{
			"id":"p3",
			"name":"Macarrones",
			"price":"2.5",
			"rating":"4",
			"quantity":"1991",
			"expirationDate": "2023-05-18",
			"type": "FOOD"
		},
		{
			"id":"p4",
			"name":"Zapatos",
			"price":"30",
			"rating":"3",
			"quantity":"1991",
			"expirationDate": "2023-05-18",
			"type": "FOOD"
		}
		],
	"orders": [
		{
			"id":"p5",
			"dateStart":"2023-05-18",
			"dateDelivery": "2023-05-20",
			"shippingCost":"5",
			"user": {
				"id": "u3",
				"name": "Maria",
				"email": "aa@aiss.com",
				"address": "baños, 33"
			},
			"products":[
				{
					"id":"p3",
					"name":"Macarrones",
					"price":"2.5",
					"rating":"4",
					"quantity":"1991",
					"expirationDate": "2023-05-18",
					"type": "FOOD"
				},
				{
					"id":"p4",
					"name":"Zapatos",
					"price":"30",
					"rating":"3",
					"quantity":"1991",
					"expirationDate": "2023-05-18",
					"type": "FOOD"
				}
		]
}]
}
```
### Recurso Market ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET | /markets  | Ver todos los supermercados existentes. •	Es posible ordenar los pedidos por fecha con el parámetro de query “order”, que solo acepta dos valores, “dateStart” o “-dateStart” que devuelve los pedidos entregados o no entregados. •	También es posible filtrar los pedidos devueltas con dos parámetros de query: “order”, que solo acepta dos valores, “delivered” o “-delivered” que devuelve los pedidos entregados o no entregados.; “name”, que devuelve los pedidos cuyo nombre coincida exactamente con el valor del parámetro. |
| GET | /orders/{orderId} | Devuelve el pedido con i:orderId. Si el pedido no existe devuelve un “404 Not Found”. |
| POST | /orders | Añadir un nuevo pedido. Los datos de el pedido (nombre y descripción) se proporcionan en el cuerpo de la petición en formato JSON. Las canciones de el pedido no se pueden incluir aquí, para ello se debe usar  la operación POST específica para añadir una producto a una lista (a continuación). Si el nombre de el pedido no es válido (nulo o vacío), o se intenta crear una lista con canciones, devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de el pedido. |
| PUT | /orders | Actualiza el pedido cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id de el pedido).  Si el pedido no existe, devuelve un “404 Not Found”. Si se intenta actualizar las canciones de el pedido, devuelve un error “400 Bad Request”. Para actualizar las canciones se debe usar el recurso product mostrado previamente. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /orders/{orderId} | Elimina el pedido con i:orderId. Si el pedido no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| POST |  /orders/{orderId}/{productId} | Añade el producto con i:productId a el pedido con i:orderId. Si el pedido o el producto no existe, devuelve un “404 Not Found”. Si el producto ya está incluida en el pedido devuelve un “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de el pedido. |
| DELETE | /orders/{orderId}/{productId}  | Elimina el producto con i:productId de el pedido con i:orderId. Si el pedido o el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.|


Una **lista** tiene un _identificador, nombre, descripción y un conjunto de canciones_. La representación JSON de este recurso es:

```cpp
{
	"id":"m1",
	"name":"El Corte Inglés",
	"description": "Ya es primavera en El Corte Inglés",
	"products": {
		"id": "u3",
		"name": "Maria",
		"email": "aa@aiss.com",
		"address": "baños, 33"
	},
	"products":[
		{
			"id":"p3",
			"name":"Macarrones",
			"price":"2.5",
			"rating":"4",
			"quantity":"1991",
			"expirationDate": "2023-05-18",
			"type": "FOOD"
		},
		{
			"id":"p4",
			"name":"Zapatos",
			"price":"30",
			"rating":"3",
			"quantity":"1991",
			"expirationDate": "2023-05-18",
			"type": "FOOD"
		}
	],
	"orders": [
		{
			"id":"p5",
			"dateStart":"2023-05-18",
			"dateDelivery": "2023-05-20",
			"shippingCost":"5",
			"user": {
				"id": "u3",
				"name": "Maria",
				"email": "aa@aiss.com",
				"address": "baños, 33"
			},
			"products":[
				{
					"id":"p3",
					"name":"Macarrones",
					"price":"2.5",
					"rating":"4",
					"quantity":"1991",
					"expirationDate": "2023-05-18",
					"type": "FOOD"
				},
				{
					"id":"p4",
					"name":"Zapatos",
					"price":"30",
					"rating":"3",
					"quantity":"1991",
					"expirationDate": "2023-05-18",
					"type": "FOOD"
				}
		]
			
}

```

**Ejemplo de mercado individual JSON**
