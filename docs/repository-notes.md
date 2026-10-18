# Atelier 3 — Spring Data JPA : Repository et opérations CRUD

**Projet :** AutoLoc  
**Package :** `tn.esprit.autoloc.repository`

## 1. Choix de l'interface Repository

Pour le projet AutoLoc, nous avons choisi `JpaRepository` pour les neuf entités.

Cette interface permet d'effectuer les opérations CRUD (Create, Read, Update, Delete), de récupérer des listes, de trier et de paginer les résultats.

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | `JpaRepository<Agence, Long>` | Gestion CRUD des agences. |
| IEmployeRepository | `JpaRepository<Employe, Long>` | Gestion CRUD des employés. |
| IVehiculeRepository | `JpaRepository<Vehicule, Long>` | Gestion CRUD des véhicules, avec tri et pagination. |
| IEquipementRepository | `JpaRepository<Equipement, Long>` | Gestion CRUD des équipements. |
| IClientRepository | `JpaRepository<Client, Long>` | Gestion CRUD des clients. |
| IReservationRepository | `JpaRepository<Reservation, Long>` | Gestion CRUD des réservations. |
| IContratRepository | `JpaRepository<Contrat, Long>` | CRUD complet, `findAll()` retourne une liste et `saveAndFlush()` est disponible. |
| IPaiementRepository | `JpaRepository<Paiement, Long>` | Consultation des paiements ; leur création et suppression passent par le contrat dans la couche métier. |
| IMaintenanceRepository | `JpaRepository<Maintenance, Long>` | Gestion CRUD des maintenances. |

## 2. Comparaison des interfaces Spring Data

| Interface | Rôle |
|---|---|
| Repository | Interface de base permettant de déclarer uniquement les méthodes nécessaires. |
| CrudRepository | Fournit les opérations CRUD de base. |
| ListCrudRepository | Fournit les opérations CRUD avec des résultats sous forme de `List`. |
| PagingAndSortingRepository | Permet le tri et la pagination. |
| JpaRepository | Regroupe les opérations CRUD, les listes, le tri, la pagination et les fonctionnalités JPA. |

**Conclusion :** `JpaRepository` est la plus adaptée au projet AutoLoc, car elle regroupe toutes les fonctionnalités nécessaires.

## 3. Opérations CRUD — Réponses théoriques

Les comportements suivants correspondent aux résultats attendus selon le cours. Ils n'ont pas été vérifiés par des tests spécifiques dans cet atelier.

### save()

La méthode `save()` permet d'ajouter ou de modifier une entité.

- Si l'identifiant est `null`, Hibernate effectue une insertion (`INSERT`).
- Si l'identifiant correspond à une entité existante, Hibernate peut effectuer une lecture (`SELECT`), puis une mise à jour (`UPDATE`) si nécessaire.

### findById()

La méthode `findById(id)` permet de rechercher une entité par son identifiant.

Elle retourne un `Optional` :

- Si l'entité existe, elle contient l'objet recherché.
- Sinon, elle retourne `Optional.empty()`.

### findAll()

La méthode `findAll()` permet de récupérer toutes les entités.

Avec `JpaRepository`, elle retourne une `List`.

### deleteById()

La méthode `deleteById(id)` permet de supprimer une entité par son identifiant.

Selon le comportement présenté dans le cours pour Spring Data 3, un identifiant inexistant ne provoque pas d'exception.

### Cascade et orphanRemoval

Dans la relation entre `Contrat` et `Paiement`, la cascade permet de propager certaines opérations du contrat vers ses paiements.

`orphanRemoval = true` permet de supprimer un paiement lorsqu'il est retiré de la collection de paiements de son contrat.

Les suppressions en lot, comme `deleteAllInBatch()`, ne déclenchent pas ces mécanismes de cascade et d'`orphanRemoval`.

## 4. Vérification des repositories

Les neuf interfaces Repository ont été créées dans le package `tn.esprit.autoloc.repository`.

Chaque interface étend `JpaRepository` avec son entité et un identifiant de type `Long`.

Lors du démarrage de l'application, Spring Boot a affiché :

`Found 9 JPA repository interfaces.`

Cela confirme que les neuf repositories ont été détectés par Spring Data JPA.

Les implémentations des repositories sont générées automatiquement par Spring Data JPA. Il n'est donc pas nécessaire de créer manuellement des classes d'implémentation.

## 5. Analyse SonarQube for IDE

Le plugin SonarQube for IDE a été installé dans IntelliJ IDEA afin d'analyser la qualité du code.

Les anomalies suivantes ont été observées et corrigées pendant le développement du projet AutoLoc, dans la couche Service de l'Atelier 4.

| Anomalie observée | Règle / explication | Correction apportée |
|---|---|---|
| Injection avec `@Autowired` sur un attribut | S6813 : privilégier l'injection par constructeur. | Utilisation de `@RequiredArgsConstructor` et `private final`. |
| Utilisation de `System.out` | S106 : éviter les affichages directs dans la console. | Suppression de la méthode temporaire. |
| Conditions `if` imbriquées | S1066 : simplifier les conditions imbriquées. | Suppression de la méthode temporaire. |

**Remarque :** ces trois anomalies ont été constatées dans les services, et non dans les packages `domain` et `repository`. L'analyse spécifique demandée pour l'Atelier 3 reste donc à compléter. Aucune anomalie non observée n'est déclarée comme corrigée.

## 6. Conclusion

Dans cet atelier, nous avons mis en place la couche Repository du projet AutoLoc en utilisant Spring Data JPA.

Les neuf interfaces utilisent `JpaRepository`, ce qui permet de bénéficier des opérations CRUD sans écrire manuellement les requêtes SQL de base.

Nous avons également étudié les différences entre les interfaces Spring Data, le comportement des méthodes CRUD et les mécanismes de cascade.

La détection des neuf repositories a été confirmée au démarrage de l'application. Les démonstrations CRUD n'ont pas été exécutées dans le cadre de cet atelier.