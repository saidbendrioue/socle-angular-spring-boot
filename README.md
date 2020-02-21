Mise en place du socle

Versions Utilisés
  1. Spring Boot v2.0.3
  2. Angular v6.0.5
  3. Core UI 2.0.2 

Pour construire le projet 'mvn clean install'
Pour accéder a l'application 'cd client' puis 'mvn spring-boot:run'
L'application sera accessible sur `http://localhost:8080`.

Fonctionalités :

1. Project maven multi module pour gérer le code du front et du back séparément 
2. Frontend injecté comme un WebJar.
3. CORS activé: une configuration globale est ajoutée pour activer CORS afin que le frontend puisse fonctionner de manière transparente avec le backend pendant le développement.
4. Spring Security avec JWT
5. validation des Form avec les annotations
6. Single Sign-On
7. Intelligent query language [specifications] pour l'utilisation des filtres.
9. Generation du code avec Lombok et MapStruct
