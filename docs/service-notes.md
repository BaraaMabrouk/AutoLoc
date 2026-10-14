# AutoLoc — Atelier 4 : IoC, Injection de dépendances et couche Service

## A. Comparaison des modes d'injection

Dans cet atelier, nous avons comparé deux méthodes d'injection de dépendances dans Spring Boot : l'injection par attribut et l'injection par constructeur.

| Critère | Injection par attribut (`@Autowired`) | Injection par constructeur (`@RequiredArgsConstructor`) |
|---|---|---|
| Champ `final` | Non | Oui |
| Dépendance visible | Moins explicite | Explicite dans le constructeur |
| Utilisation hors du conteneur Spring | Difficile | Possible en fournissant la dépendance |
| SonarQube | Anomalie S6813 détectée | Méthode recommandée |

**Conclusion :** nous avons choisi l'injection par constructeur avec Lombok `@RequiredArgsConstructor`, car elle rend les dépendances explicites, facilite les tests et permet d'utiliser des attributs `final`.

## B. Services et dépendances injectées

Nous avons créé neuf interfaces Service et neuf classes d'implémentation. Chaque service utilise son repository à travers une interface.

| Service | Dépendance injectée | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Gérer les agences |
| ClientServiceImpl | IClientRepository | Constructeur | Gérer les clients |
| ContratServiceImpl | IContratRepository | Constructeur | Gérer les contrats |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Gérer les employés |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Gérer les équipements |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Gérer les maintenances |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Consulter les paiements |
| ReservationServiceImpl | IReservationRepository | Constructeur | Gérer les réservations |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Gérer les véhicules |

**Justification :** l'injection par constructeur permet de réduire le couplage et facilite la maintenance du projet. Chaque service dépend d'une interface Repository plutôt que d'une implémentation concrète.

## C. Analyse des trois erreurs Spring

### 1. Service introuvable

**Cause :** Spring ne trouve pas le bean demandé, par exemple lorsque l'annotation `@Service` est absente de la classe d'implémentation.

**Correction :** ajouter `@Service` et vérifier que la classe se trouve dans un package analysé par Spring.

### 2. Plusieurs beans du même type

**Cause :** plusieurs classes implémentent la même interface. Spring ne sait pas quelle implémentation injecter.

**Correction :** utiliser `@Primary` pour définir une implémentation prioritaire ou `@Qualifier` pour choisir précisément le bean.

### 3. Dépendance circulaire

**Cause :** deux services dépendent directement ou indirectement l'un de l'autre, ce qui peut empêcher leur création.

**Correction :** réorganiser les responsabilités des services afin de supprimer la dépendance circulaire.

Ces trois erreurs ont été étudiées pour comprendre le fonctionnement du conteneur Spring et les problèmes possibles lors de l'injection de dépendances.

## D. Anomalies détectées avec SonarQube for IDE

Nous avons utilisé SonarQube for IDE dans IntelliJ IDEA pour analyser la qualité du code Java.

### Anomalie 1 — S6813 : Injection par attribut

**Problème détecté :** utilisation de `@Autowired` directement sur un attribut.

**Explication :** cette méthode rend les dépendances moins explicites et complique les tests.

**Correction :** remplacer l'injection par attribut par l'injection par constructeur avec `@RequiredArgsConstructor` et un attribut `final`.

### Anomalie 2 — S106 : Utilisation de System.out

**Problème détecté :** utilisation de `System.out.println()` dans une méthode temporaire.

**Explication :** l'affichage direct dans la console n'est pas recommandé pour gérer les journaux d'une application.

**Correction :** utiliser un logger dans les traitements nécessitant une journalisation. La méthode temporaire utilisée pour l'expérience a été supprimée.

### Anomalie 3 — S1066 : Conditions if imbriquées

**Problème détecté :** deux instructions `if` imbriquées pouvant être fusionnées.

**Explication :** une imbrication inutile augmente la complexité et réduit la lisibilité du code.

**Correction :** fusionner les conditions avec l'opérateur logique `&&` lorsque cela conserve le même comportement. La méthode temporaire utilisée pour l'expérience a été supprimée.

**Conclusion :** les trois anomalies ont été observées avec SonarQube. Après les expériences, le code initial a été restauré et les méthodes temporaires supprimées.

## E. Questions de compréhension

### 1. Où est le `new` de ContratServiceImpl et qui l'exécute ?

Nous n'écrivons pas directement `new ContratServiceImpl()` dans notre application. C'est le conteneur Spring qui crée et gère automatiquement l'objet grâce à l'annotation `@Service` et à l'injection de dépendances.

### 2. Pourquoi un contrôleur dépendra-t-il de IContratService et non de ContratServiceImpl ?

Parce que dépendre d'une interface réduit le couplage entre les classes. Cela permet de changer l'implémentation du service sans modifier le contrôleur et facilite les tests.

### 3. Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.

Un service singleton est partagé entre plusieurs utilisateurs ou requêtes. Il doit rester sans état mutable propre à chaque requête pour éviter les conflits.

**Exemple :** si un service conserve le dernier identifiant client dans un attribut, une autre requête pourrait modifier cette valeur et provoquer une confusion entre les clients.

### 4. Pourquoi update charge-t-il l'entité existante avant d'appeler save ?

La méthode `update` charge d'abord l'entité existante afin de vérifier son existence et de modifier uniquement les champs autorisés.

Cela permet de conserver son identifiant et ses relations avec les autres entités, au lieu de remplacer directement l'entité par l'objet reçu.

### 5. Quelle différence entre @Component et @Bean ? Entre @Primary et @Qualifier ? Pourquoi IPaiementService n'expose-t-il ni création, ni modification, ni suppression ?

**@Component et @Bean :**

- `@Component` est placé sur une classe pour permettre à Spring de la détecter et de la gérer automatiquement.
- `@Bean` est placé sur une méthode de configuration pour déclarer explicitement un objet géré par Spring.

**@Primary et @Qualifier :**

- `@Primary` indique quel bean utiliser par défaut lorsqu'il existe plusieurs candidats.
- `@Qualifier` permet de sélectionner précisément le bean souhaité.

**IPaiementService :**

Dans cet atelier, le service Paiement est volontairement limité à la consultation. Il expose uniquement `findById()` et `findAll()`.

Les opérations de création, modification et suppression ne font donc pas partie de son interface.

---

## Conclusion générale

Cet atelier nous a permis de mettre en place la couche Service du projet AutoLoc en utilisant Spring Boot, les interfaces, l'injection par constructeur et les repositories JPA.

Nous avons également étudié le fonctionnement du conteneur Spring, analysé trois situations d'erreur et détecté trois anomalies avec SonarQube for IDE.

L'application a démarré correctement sur le port 8081, avec neuf repositories JPA détectés et une connexion à la base de données établie.