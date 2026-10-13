# AutoLoc — Stratégies Fetch et Cascade

## Justification des associations JPA

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement dépend de son contrat. La suppression du contrat entraîne la suppression de ses paiements. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule peut exister indépendamment de son agence. |
| Agence → Employe | LAZY | Aucune | Les employés ne doivent pas être supprimés automatiquement avec leur agence. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules. |
| Client → Reservation | LAZY | PERSIST | L'enregistrement d'un client peut propager l'enregistrement de nouvelles réservations. |
| Reservation → Vehicule | LAZY | Aucune | Un véhicule existe indépendamment de ses réservations. |
| Reservation ↔ Contrat | LAZY | ALL côté Reservation | Le contrat est lié au cycle de vie de sa réservation. |
| Vehicule → Maintenance | LAZY | PERSIST | L'enregistrement d'un véhicule peut propager l'enregistrement de nouvelles maintenances. |

## Conclusion

Le chargement LAZY est retenu pour limiter le chargement automatique des entités associées.

Les cascades sont choisies selon les dépendances entre les entités du modèle AutoLoc.

L'option orphanRemoval est utilisée pour supprimer les paiements retirés de la collection d'un contrat.