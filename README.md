# cia-jpa

Primera parte del proyecto: Captura y almacenamiento.

Notas importantes:
Para la conexion con la BD postgreSQL se debe configurar los siguientes parametros de la aplicacion, con los valores que seran utilizados en el ambiente de prueba (en src/main/resources/application.properties):

spring.datasource.url=jdbc:postgresql://localhost/testdb

spring.datasource.username=test

spring.datasource.password=test


El archivo midbpostgresql , contiene a la bd comprimida lista para importar.