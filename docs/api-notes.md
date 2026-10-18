# Atelier 5 — Notes sur l'API REST AutoLoc

## A. Tableau des endpoints REST

L'application AutoLoc expose des API REST permettant de gérer les agences, employés, véhicules, équipements, clients, réservations, contrats, paiements et maintenances.

L'URL de base est `http://localhost:8081/auto-loc`.

| Contrôleur | Méthodes HTTP | Route | Service utilisé | Réponses |
|---|---|---|---|---|
| AgenceController | GET, POST, PUT, DELETE | `/agences` | IAgenceService | 200, 201, 204, 400, 404 |
| EmployeController | GET, POST, PUT, DELETE | `/employes` | IEmployeService | 200, 201, 204, 400, 404 |
| VehiculeController | GET, POST, PUT, DELETE | `/vehicules` | IVehiculeService | 200, 201, 204, 400, 404 |
| EquipementController | GET, POST, PUT, DELETE | `/equipements` | IEquipementService | 200, 201, 204, 400, 404 |
| ClientController | GET, POST, PUT, DELETE | `/clients` | IClientService | 200, 201, 204, 400, 404 |
| ReservationController | GET, POST, PUT, DELETE | `/reservations` | IReservationService | 200, 201, 204, 400, 404 |
| ContratController | GET, POST, PUT, DELETE | `/contrats` | IContratService | 200, 201, 204, 400, 404 |
| PaiementController | GET | `/paiements` | IPaiementService | 200, 404 ; POST : 405 |
| MaintenanceController | GET, POST, PUT, DELETE | `/maintenances` | IMaintenanceService | 200, 201, 204, 400, 404 |

Les contrôleurs CRUD proposent GET sur la collection, GET par identifiant, POST, PUT et DELETE.

## B. Gestion des relations JSON avec Jackson

Les entités JPA possèdent des relations bidirectionnelles pouvant provoquer des boucles infinies lors de la sérialisation JSON.

Nous avons utilisé :

- `@JsonIgnore` sur certaines collections inverses, notamment `Agence.vehicules`, `Agence.employes`, `Client.reservations`, `Equipement.vehicules`, `Vehicule.reservations` et `Vehicule.maintenances`.
- `@JsonManagedReference("contrat-paiements")` sur `Contrat.paiements`.
- `@JsonBackReference("contrat-paiements")` sur `Paiement.contrat`.

`@JsonIgnore` masque une propriété lors de la sérialisation JSON. `@JsonManagedReference` et `@JsonBackReference` permettent de gérer les deux côtés d'une relation parent-enfant, en conservant notamment les paiements dans le JSON d'un contrat sans répéter le contrat dans chaque paiement.

Ces annotations ne suppriment pas les associations JPA dans MySQL.

## C. Postman et Swagger

Postman permet d'envoyer des requêtes HTTP, de fournir un corps JSON et de vérifier les réponses réelles du serveur.

Swagger UI permet de consulter la documentation des endpoints, les paramètres, les schémas et les codes de réponse annoncés. Il permet également d'essayer les opérations.

Exemple testé avec Postman : création d'un contrat avec des paiements associés. Le serveur a retourné `201 Created`, et les paiements ont été enregistrés avec la clé étrangère du contrat.

La documentation Swagger est accessible à l'adresse :

`http://localhost:8081/auto-loc/swagger-ui.html`

## D. Analyse SonarQube for IDE

L'analyse initiale du package des contrôleurs a détecté 52 problèmes.

**Première correction — S1710 :** SonarQube signalait l'utilisation de conteneurs `@ApiResponses` inutiles. Nous les avons remplacés par des annotations `@ApiResponse` répétées. Résultat : 34 problèmes corrigés.

**Deuxième correction — Fuseau horaire :** dans `GlobalExceptionHandler.java`, SonarQube signalait deux appels à `LocalDateTime.now()` sans fuseau horaire explicite. Nous les avons remplacés par `LocalDateTime.now(ZoneId.of("Africa/Tunis"))`. Résultat : deux problèmes corrigés.

**Amélioration future — S4684 :** 18 avertissements signalent l'exposition directe d'entités JPA dans les contrôleurs REST. Une amélioration possible serait d'introduire des DTO et des conversions entre DTO et entités. Cette refonte n'a pas été effectuée dans cet atelier.

## E. Réponses aux questions théoriques

### 1. Rôle de DispatcherServlet et HandlerMapping

`DispatcherServlet` est le point d'entrée central des requêtes HTTP dans Spring MVC. `HandlerMapping` permet d'identifier le contrôleur et la méthode correspondant à la requête.

### 2. Pourquoi injecter une interface de service ?

Injecter une interface comme `IContratService` permet de réduire le couplage entre le contrôleur et l'implémentation du service. Le contrôleur reste responsable des requêtes HTTP, tandis que le service gère la logique métier.

### 3. Différence entre les codes HTTP

- `201 Created` : une nouvelle ressource a été créée.
- `204 No Content` : une opération a réussi sans corps de réponse, notamment DELETE.
- `404 Not Found` : la ressource demandée est introuvable.
- `500 Internal Server Error` : une erreur inattendue est survenue côté serveur.

Une ressource inexistante doit normalement produire 404 et non 500.

### 4. Pourquoi PaiementController expose uniquement GET ?

Les paiements sont créés à travers les contrats grâce à la relation JPA et au mécanisme de cascade. `PaiementController` permet seulement leur consultation.

Une requête POST vers `/paiements` retourne `405 Method Not Allowed`, car aucune méthode POST n'est définie sur cette route.

### 5. Risques de l'exposition directe des entités JPA

Exposer directement les entités JPA peut entraîner des boucles JSON, des problèmes de chargement paresseux, une exposition de données non souhaitées et un couplage fort entre la base de données et l'API.

Pour la relation `Contrat.paiements`, `@JsonManagedReference` permet de conserver les paiements dans la réponse JSON du contrat, tandis que `@JsonBackReference` évite la récursion inverse.

Pour `Agence.vehicules`, `@JsonIgnore` est adapté à notre besoin actuel, car la liste des véhicules n'a pas besoin d'être incluse dans la réponse JSON d'une agence.

## Conclusion

L'Atelier 5 a permis d'exposer les services AutoLoc à travers des contrôleurs REST, de tester les requêtes HTTP avec Postman, de documenter les endpoints avec SpringDoc/Swagger, de gérer les relations JSON avec Jackson et d'améliorer la qualité du code grâce à SonarQube for IDE.
