To Test application start locally it needs to perform several steps:

1. Clone sources to local direcrory
	$  git clone https://github.com/vbezmenov/eKar.git
2. Start DB
	$ docker-compose -f ./db/docker-compose.yml up -d
3. Run docker instance with application
	$ mvn spring-boot:run

Web interface is available at http://127.0.0.1:8080/swagger-ui.htm
