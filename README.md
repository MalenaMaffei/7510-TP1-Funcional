# 7510-TP1-Funcional
### Intérprete Lógico

## Uso

```
evaluate-query <database> <query>
```

donde **database** es una cadena de hechos y reglas separados por "." y **query** la consulta que se le quiera hacer a dicha **database**

Los hechos pueden tener cualquier numero de parametros.
Las reglas deben tener variables compuestas de un solo caracter en mayúsculas y deben estar compuestas por hechos válidos.

* se devolvera **nil** cuando el formato de la database o de las queries no sean validos, o cuando al ser la query parte de una regla, sea incorrrecto el número de parámetros.

* si la regla o el hecho no existen, o si los hechos que componen a la regla no se cumplen, se devolvera **false**

* si la regla se cumple o los hechos sean verdaderos, se devolvera **true**

## Módulos

#### syntax-validator
Se encarga de verificar que el formato de las queries y de las entries de la database sean de la forma correcta.

### parser
Se encarga de extraer de la database y las queries y formatearlas para poder resolver la consulta

### logical-interpreter
Contiene la lógica para resolver las consultas.

## Correr Tests
lein test
