# Filmland Sogeti

## Development

Om dit project te starten, run je het volgende commando in de terminal:

```shell
nvm spring-boot:run
```

# Aanpak

## 1

Als eerste heb ik gekeken naar de gehele opdracht om te ontdekken wat er precies moet gebeuren. Als omgeving heb ik gekozen
voor Spring Boot, omdat ik hier al bekend mee ben. Ik heb dan juist weer gekozen voor Maven, omdat ik dat nog niet ken.
Dit assessment is een mooie stap om daar ook kennis mee te maken. Ik heb alle dependencies toegevoegd, waaronder als extra
dependency Lombok. Ik gebruik dit graag, omdat je dan geen getters en setters e.d. hoeft te schrijven. Alle dependencies 
staan in de pom.xml.

## 2

Uit de opdracht maak ik op dat er gewerkt wordt met verschillende tabellen. Ik heb gekozen voor een User tabel, Category 
tabel en een Subscription tabel. Ik heb hier classes van gemaakt en de velden toegevoegd die ik in de opdracht kon vinden. 
Deze heb ik ondergebracht in het mapje Entitites.

## 3

Begonnen met opdracht 1. Het ging hier om Users, dus ik heb een mapje 'users' aangemaakt om alle classes in te zetten.
Als eerste de UserController opgezet, waar de endpoint in komt. Omdat de requestbody als een JSON binnenkomt, heb ik
een aparte class gemaakt: UserCredentials. Deze bevat de informatie die ook binnenkomt (email & password).

## 4

Ik merkte dat de response vaak hetzelfde is, daar heb ik ook een class voor aangemaakt in het mapje Response. Ik heb daar 
ook de afwijkende response van de Category opdracht ingezet. Deze bevatten alle info die terug moet worden gegeven.

## 5

Vervolgens de rest van de opdracht uitgewerkt en deze getest met Postman. Ik heb ook gebruik gemaakt van Google en ChatGPT 
als ik vast zat of meldingen kreeg die ik niet goed begreep.

