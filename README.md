# Project

##### Launch with

    mvn clean install spring-boot:run


##### Postman Collection:

See ***ESIPE_DM-ONLINE-BANKING.postman_collection.json***



##### Account types:

* Livret A (A)
* Livret de développement durable et solidaire (LDDS)
* Livret d'épargne populaire (LEP)
* Livret jeune (LJ)
* Compte épargne logement (CEL)
* Plan épargne logement (PEL)

To see MySQL Logs (for transactions):
    SET GLOBAL general_log = 'ON';


/*

postman:http://localhost:25001/client-authentification?role=ROLE_BANK_CLIENT
log: [ZuulFilterImplementation][GET][http://localhost:25001/client-authentification/client-service]


http://localhost:25001/advisor-authentification?role=ROLE_BANK_ADVISOR
log: [ZuulFilterImplementation][GET][http://localhost:25001/advisor-authentification/advisor-management]


par la suite il faut faire évoluer le controller du frontapi
pour rediriger les appels à client-authentification et advisor-authentification
sur les services internes respectives, sur les ports 25003 et 25004
 */