# AutoLoc

## Description

AutoLoc est une plateforme de gestion de location de véhicules multi-agences.

Ce projet est développé dans le cadre de l'UP Architecture des Systèmes d'Information (ASI).

## Technologies utilisées

- Java 17
- Spring Boot
- Maven
- Spring Data JPA
- MySQL
- Lombok
- Git / GitHub
- Postman
- IntelliJ IDEA

## Acteurs du système

### Client
Le client peut utiliser le système pour effectuer et gérer ses réservations de véhicules.

### Agent d'agence
L'agent d'agence participe à la gestion des locations et des véhicules de son agence.

### Responsable d'agence
Le responsable d'agence supervise les activités et la gestion de son agence.

### Administrateur
L'administrateur assure l'administration générale du système AutoLoc.

## Structure actuelle du projet

Le projet contient actuellement les entités suivantes :

- Vehicule
- Agence
- Client
- Employe
- Equipement
- Reservation
- Contrat
- Paiement
- Maintenance

## Configuration de développement

Le projet utilise un profil Spring Boot `dev` avec le fichier
`application-dev.properties`.

Ce profil contient la configuration utilisée pour l'environnement
de développement, notamment la connexion à MySQL, JPA/Hibernate
et le port du serveur.

## Données de démonstration

Un `CommandLineRunner` permet d'insérer automatiquement deux
véhicules de démonstration dans la base de données lorsque la
table `vehicule` est vide.

Ces données permettent de tester l'application sans devoir
ajouter manuellement des véhicules dans la base.

Les associations entre les différentes entités seront ajoutées lors de l'Atelier 2.