# Soccer Microservices
Students: Maxim Peeters - Devin Veraghtert
Class: 3APP02

## Inhoudsopgave
- [Thema](#thema)
- [Services](#services)
- [Deployment Schema](#deployment-schema)
- [Endpoints](#endpoints)
- [Authentication](#authentication)
- [Postman requests](#postman-requests)
- [Front-end](#front-end-1)
## Thema
Het thema van dit project is een microservices-architectuur voor een voetbalcompetitie. De backend bestaat uit verschillende services die elk een specifiek domein beheren, zoals spelers, teams, wedstrijden en supporters. Deze services communiceren met elkaar via API's en maken gebruik van verschillende databases zoals MongoDB en MySQL.

### Geimplementeerde gervices

#### player-service
De player-service beheert alle gegevens met betrekking tot de spelers. Dit omvat het toevoegen, bewerken, verwijderen en opvragen van spelersinformatie.

#### team-service
De team-service beheert alle gegevens met betrekking tot de teams. Dit omvat het toevoegen, bewerken, verwijderen en opvragen van teaminformatie. 

#### match-service
De match-service beheert alle gegevens met betrekking tot de wedstrijden. Dit omvat het toevoegen, bewerken, verwijderen en opvragen van wedstrijdinformatie. 

#### supporter-service
De supporter-service beheert alle gegevens met betrekking tot de supporters. Dit omvat het toevoegen, bewerken, verwijderen en opvragen van supportersinformatie. 

#### Frontend
De frontend is verantwoordelijk voor het presenteren van de gegevens aan de gebruikers en biedt functionaliteiten zoals het bekijken en beheren van teams, wedstrijden en supporters. De frontend communiceert met de backend services om de benodigde gegevens op te halen en acties uit te voeren.


## Deployment Schema
![Deployment](/images/Deployment.png)
## Services
### player-service
- **port**: 8080
- **database**: MongoDB
    - **port**: 27017
- **docker image name**: maximp123/player-service

### team-service
- **port**: 8081
- **database**: MySQL
    - **port**: 3306
    - **database name**: teamdb
    - **username**: root
    - **password**: abc123
- **docker image name**: maximp123/team-service

### match-service
- **port**: 8082
- **database**: MongoDB
    - **port**: 27018
- **docker image name**: maximp123/match-service

### supporter-service
- **port**: 8083
- **database**: MySQL
    - **port**: 3307
    - **database name**: supporterdb
    - **username**: root
    - **password**: abc123
- **docker image name**: maximp123/supporter-service

### api-gateway
- **port**: 8084
- **docker image name**: maximp123/api-gateway

### soccer-frontend
- **framework**: React
    - **port**: 8085
- **server**: Nginx
    - **port**: 80

## Endpoints

### teamservice
- **HTTP**: Players => get all players

### matchservice
- **HTTP**: Team => get home and away team

### supporterservice
- **HTTP**: Team, Player => get team and favorite player

## Authentication

### without auth
- **can GET**: /players, /matches, /teams (Only to view every record)

### with auth
- **every Method allowed**: on every entity

## Postman requests
Hieronder zie je alle endpoints en hun response met Postman.
### Player service
#### All players:
![All players](images/Player/GetAll.png)
#### All By Playercode:
![All By Playercode](images/Player/GetByPlayerCode.png)
#### All By Team Code:
![All By Team Code](images/Player/AllByTeamCOde.png)
#### Add New Player:
![Add New Player](images/Player/AddNewPlayer.png)
#### Edit Player:
![Edit Player](images/Player/EditPlayer.png)
#### Remove Player:
![Remove Player](images/Player/RemovePlayer.png)

### Team service
#### All Teams:
![All Teams](images/Teams/GetAllTeams.png)
#### By Team Code:
![By Team Code](images/Teams/GetByTeamCode.png)
#### Create New Team:
![Create New Team](images/Teams/CreateNewTeam.png)
#### Edit Team:
![Edit Team](images/Teams/EditTeam.png)
#### Remove Team:
![Remove Team](images/Teams/RemoveTeam.png)

### Match service
#### All Matches:
![All Matches](images/Match/GetAllMatches.png)
#### By Match Code:
![By Match Code](images/Match/GetByMatchCode.png)
#### Create New Match:
![Create New Match](images/Match/CreateNewMatch.png)
#### Edit Match:
![Edit Match](images/Match/EditMatch.png)
#### Remove Match:
![Remove Match](images/Match/RemoveMatch.png)

### Supporter service
#### All Supporters:
![All Supporters](images/Supporter/GetAll.png)
#### By Supporter Code:
![By Supporter Code](images/Supporter/GetBySupporterCode.png)
#### Create New Supporter:
![Create New Supporter](images/Supporter/CreateNewSupporter.png)
#### Edit Supporter:
![Edit Supporter](images/Supporter/EditSupporter.png)
#### Remove Supporter:
![Remove Supporter](images/Supporter/RemoveSupporter.png)

## Front-end
Front-end gemaakt met React, Nginx & Google Auth
### Home Page
![Home Page](images/Frontend/HomePage.png)

### Matches View
![Matches View](images/Frontend/MatchesView.png)

### Match Management
![Match Management](images/Frontend/MatchManagement.png)

### Supporters Management
![Supporters Management](images/Frontend/SupportersManagement.png)

### Supporters View
![Supporters View](images/Frontend/SupportersView.png)

### Teams Management
![Teams Management](images/Frontend/TeamsManagement.png)

### Teams View
![Teams View](images/Frontend/TeamsView.png)
