# FS Library — Application Android de gestion d'une bibliothèque

**Projet de fin d'études (Licence fondamentale en Sciences Mathématiques et Informatique, 2021–2022)**  
Faculté des Sciences de Tétouan — Université Abdelmalek Essaâdi

## Présentation

**FS Library** est une application mobile Android conçue pour faciliter la gestion d'une bibliothèque universitaire à distance grâce à des services web RESTful. Le projet prend pour cas d'étude la bibliothèque de la Faculté des Sciences de Tétouan.

L'application propose deux espaces : un espace **étudiant**, destiné à consulter le catalogue et à réserver des exemplaires, et un espace **bibliothécaire**, consacré à la gestion des livres, des réservations et des emprunts.

Ce travail a été réalisé dans le cadre d'un projet de fin d'études sur une période d'environ trois mois.

## Objectifs

- Faciliter l'accès des étudiants au catalogue de la bibliothèque.
- Simplifier la recherche et la réservation d'exemplaires.
- Assurer le suivi des livres réservés, empruntés et disponibles.
- Permettre au bibliothécaire de gérer les livres et de suivre les retours.
- Réduire les opérations manuelles de gestion de la bibliothèque.

## Fonctionnalités

### Espace étudiant

- Inscription et authentification.
- Consultation des livres classés par discipline et de leurs informations.
- Réservation d'exemplaires et annulation de réservations.
- Consultation des exemplaires réservés et empruntés dans un espace personnel.
- Consultation et modification de certaines informations du compte.
- Procédure de récupération du mot de passe.

### Espace bibliothécaire

- Consultation et recherche des livres par titre.
- Ajout, modification et suppression de livres.
- Suivi des réservations et confirmation de la remise d'un exemplaire à un étudiant.
- Suivi des emprunts et confirmation du retour d'un exemplaire.
- Consultation des étudiants suspendus à la suite d'un retard et gestion de leur statut.
- Gestion des informations du compte bibliothécaire.

## Technologies utilisées

| Composant | Technologies |
| --- | --- |
| Application mobile | Android, Java, XML |
| Services web | PHP, API RESTful |
| Base de données | MySQL |
| Échange de données | JSON |
| Réseau Android | Volley |
| Chargement d'images | Glide |
| Gestion des permissions | Dexter |
| Interface utilisateur | Material Design |
| Conception et modélisation | UML, Visual Paradigm |
| Outils de développement | Android Studio, XAMPP, phpMyAdmin, Visual Studio Code |

## Architecture

Le système repose sur une **architecture à trois tiers** :

```text
Application Android (Java / XML)
            |
            | Requêtes HTTP / réponses JSON
            v
Services web RESTful (PHP)
            |
            v
Base de données MySQL
```

- **Client Android :** présente les interfaces et permet aux utilisateurs d'effectuer leurs opérations.
- **Serveur web PHP :** assure les échanges entre l'application mobile et la base de données.
- **Base MySQL :** conserve les informations relatives aux livres, aux utilisateurs, aux réservations et aux emprunts.

La conception s'appuie également sur les principes du modèle **MVC (Modèle–Vue–Contrôleur)** et sur plusieurs diagrammes UML : cas d'utilisation, séquence, classes et entité-association.

## Démarche de conception

Le projet a suivi une démarche de développement en cascade comprenant l'analyse des besoins, l'étude de solutions existantes, la conception, l'implémentation et la validation. La modélisation UML a permis de représenter les interactions entre les deux types d'utilisateurs et les principales opérations du système.

## Limites et pistes d'amélioration

Le rapport de projet identifie plusieurs difficultés techniques et possibilités d'évolution :

- **Notifications :** l'envoi automatique de notifications depuis la base de données n'a pas pu être finalisé.
- **Actualisation des données :** MySQL n'étant pas utilisé comme une base de données temps réel, un mécanisme d'actualisation périodique avec `Handler` a été adopté comme solution de contournement.
- **Sécurité :** un renforcement de la sécurité de la base de données et une limitation des tentatives d'authentification ont été identifiés comme des améliorations nécessaires.

Ce dépôt présente un **projet académique réalisé en 2022**, et non une solution de gestion de bibliothèque déployée en production.

## Documentation

Le rapport de fin d'études détaille la problématique, l'analyse des besoins, les diagrammes de conception, l'architecture technique, les interfaces de l'application et les difficultés rencontrées.

> Le rapport et les captures d'écran peuvent être ajoutés au dépôt dans un dossier `docs/` afin de compléter cette présentation.

## Auteurs et encadrement

**Réalisé par :**
- Ali EL MOUSSAOUI
- Ismail EL MORABET

**Encadrant :** Pr. Mohamed Larbi Ben Maâti  
**Établissement :** Faculté des Sciences de Tétouan, Université Abdelmalek Essaâdi  
**Année universitaire :** 2021–2022

---

*Projet universitaire réalisé dans le cadre de la Licence fondamentale en Sciences Mathématiques et Informatique (SMI).*
