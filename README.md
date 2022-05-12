# Implementación de una API REST 

Proyecto de Integracion de la asignutara AISS.

La API REST estará formada por cuatro recursos que permitirán manipular grandes almacenes, pedidos a estos almacenes, los productos que almacenan y la informacion del usuario actual.

El contrato de servicios de los productos se detalla a continuación.

### Recurso Product ###
| HTTP  | URI | Descripción | Ejemplo |
| ------------- | ------------- | ------------- | ------------- |
| GET |  /product | Devuelve todos los productos de la aplicación. •	Es posible ordenar los productos por la categoria con el parámetro de query “order”, que acepta los valores “price”, “-price”, “quantity” o “-quantity”. •	También es posible filtrar los productos devueltas con el parámetro de query “q”, que devuelve aquellos productos cuya categoria, nombre contengan la cadena enviada (ignorando mayúsculas y minúsculas), o los valores “price”, “-price”, “rating” o “-rating” y “quantity” o “-quantity” que devuelve los productos que tengan el precio o la valoracion mayor o menor que la cadena enviada.| ```cpp
{
	"products": [
	{
		"id":"p3",
		"name":"Macarrones",
		"price":"2.5",
		"rating":"4",
		"quantity":"1991",
		"expirationDate": "2023-05-18",
		"type": "FOOD"
	}
	]
}
``` |
| GET | /product/{productId}  |  Devuelve el producto con id=productId. Si el producto no existe devuelve un “404 Not Found”. | ```cpp
{
	"id":"p3",
	"name":"Macarrones",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18",
	"type": "FOOD"
}
``` |
| POST | /product | Añade un nuevo producto cuyos datos se pasan en el cuerpo de la petición en formato JSON (no se debe pasar id, se genera automáticamente). Si el nombre del producto no es válido (null o vacío), si la fecha de caducidad no es valida (anterior a la fecha actual o vacío), si el precio o la cantidad son negativos devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido del producto. |  ```cpp
{
	"id":"p3",
	"name":"Macarrones",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18",
	"type": "FOOD"
}
``` |
| PUT | /product  | Actualiza el producto cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id del producto). Si el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. | ```cpp
{
	"id":"p3",
	"price":"2.5",
	"rating":"4",
	"quantity":"1991",
	"expirationDate": "2023-05-18"
}
``` |
| DELETE | /product/{productId}  |  Elimina el producto con id=productId. Si el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.| ```cpp
{
	"id":"p3"
}
``` |

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


### Recurso Playlist ###
| HTTP  | URI | Descripción |
| ------------- | ------------- | ------------- |
| GET | /lists  | Ver todas las listas de reproducción existentes. •	Es posible ordenar las listas por nombre con el parámetro de query “order”, que solo acepta dos valores, “name” o “-name”. •	También es posible filtrar las listas devueltas con dos parámetros de query: “isEmpty”, que devuelve listas sin canciones si vale “true” o listas con canciones si vale “false”; “name”, que devuelve las listas cuyo nombre coincida exactamente con el valor del parámetro. |
| GET | /lists/{playlistId} | Devuelve la lista con id=playlistId. Si la lista no existe devuelve un “404 Not Found”. |
| POST | /lists | Añadir una nueva lista de reproducción. Los datos de la lista (nombre y descripción) se proporcionan en el cuerpo de la petición en formato JSON. Las canciones de la lista no se pueden incluir aquí, para ello se debe usar  la operación POST específica para añadir una producto a una lista (a continuación). Si el nombre de la lista no es válido (nulo o vacío), o se intenta crear una lista con canciones, devuelve un error “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de la lista. |
| PUT | /lists | Actualiza la lista cuyos datos se pasan en el cuerpo de la petición en formato JSON (deben incluir el id de la lista).  Si la lista no existe, devuelve un “404 Not Found”. Si se intenta actualizar las canciones de la lista, devuelve un error “400 Bad Request”. Para actualizar las canciones se debe usar el recurso product mostrado previamente. Si se realiza correctamente, devuelve “204 No Content”. |
| DELETE | /lists/{playlistId} | Elimina la lista con id=playlistId. Si la lista no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”. |
| POST |  /lists/{playlistId}/{productId} | Añade el producto con id=productId a la lista con id=playlistId. Si la lista o el producto no existe, devuelve un “404 Not Found”. Si el producto ya está incluida en la lista devuelve un “400 Bad Request”. Si se añade satisfactoriamente, devuelve “201 Created” con la referencia a la URI y el contenido de la lista. |
| DELETE | /lists/{playlistId}/{productId}  | Elimina el producto con id=productId de la lista con id=playlistId. Si la lista o el producto no existe, devuelve un “404 Not Found”. Si se realiza correctamente, devuelve “204 No Content”.|


Una **lista de reproducción** tiene un _identificador, nombre, descripción y un conjunto de canciones_. La representación JSON de este recurso es:

```cpp
{
	"id":"p5",
	"name":"AISSPlayList",
	"description":"AISS PlayList",
	"products":[
		{
			"id":"s0",
			"title":"Rolling in the Deep",
			"artist":"Adele",
			"album":"21",
			"year":"2011"
		},

		{			
			"id":"s1",
			"title":"One",
			"artist":"U2",
			"album":"Achtung Baby",
			"year":"1992"
		}
		]
}

```
