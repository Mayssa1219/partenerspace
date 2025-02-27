
# **Miravia**

Miravia est une plateforme qui connecte les bénévoles, les donateurs et les organisateurs d'événements pour des causes sociales. Elle permet aux utilisateurs de s'inscrire à des événements, de faire des dons ou de se porter bénévoles, tandis que les organisateurs (partenaires) peuvent créer et gérer des événements. La plateforme est construite avec JavaFX, Spring Boot et Oracle Database.

---

### **Vue d'ensemble du projet**

Miravia est une plateforme sociale conçue pour aider les bénévoles, les donateurs et les organisateurs d'événements à collaborer pour diverses causes. Elle sert trois types d'utilisateurs principaux :
- **Admins** : Gèrent la plateforme, les utilisateurs et les événements.
- **Partenaires** : Organisent des événements, visualisent les bénévoles et les donateurs, et gèrent les activités liées aux événements.
- **Utilisateurs** : Les utilisateurs réguliers peuvent s'inscrire à des événements, faire des dons ou se porter bénévoles pour différentes activités.

---

### **Technologies Utilisées**
- **JavaFX** : Pour la création de l'interface utilisateur de la plateforme.
- **Spring Boot** : Framework backend pour développer des API REST et gérer la logique métier.
- **Oracle Database** : Base de données utilisée pour stocker les informations concernant les utilisateurs, les événements et les dons.
- **Spring Security** : Pour l'authentification des utilisateurs et l'autorisation basée sur les rôles.
- **Maven** : Pour la gestion des dépendances du projet.

---

### **Installation et Configuration**

#### **1. Cloner le Dépôt**

Pour commencer avec Miravia, clonez le dépôt depuis GitHub :

```bash
git clone https://github.com/tonnomutilisateur/Miravia.git
```

#### **2. Configurer la Base de Données**

Miravia utilise Oracle comme base de données. Pour la configurer, suivez ces étapes :
1. Créez une base de données Oracle.
2. Utilisez le fichier `miravia.sql` situé dans le dossier `` pour créer les tables nécessaires.

#### **3. Configuration**

Dans le fichier `application.properties`, configurez la connexion à la base de données et autres paramètres :

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=tonnomutilisateur
spring.datasource.password=tonmotdepasse
spring.jpa.hibernate.ddl-auto=update
```



### **Structure du Backend**

- **Contrôleurs** : Gèrent les requêtes HTTP et les réponses.
- **Services** : Couche logique métier qui gère la fonctionnalité principale de l'application.
- **Répertoires** : Responsables de l'accès aux données et des opérations sur les entités.
- **Modèles** : Modèles de données représentant les entités de la base de données telles que les événements, les utilisateurs, les dons.

---

### **Structure du Frontend**

- **Fichiers FXML** : Composants JavaFX pour l'interaction avec l'utilisateur.
- **Contrôleurs** : Classes Java qui gèrent l'interaction entre l'interface utilisateur et le backend.
- **CSS** : Styles pour personnaliser l'apparence de l'interface utilisateur.

### **Acknowledgments**

- **Spring Boot** : Pour fournir une manière facile de créer des applications basées sur Spring.
- **JavaFX** : Pour la création de l'interface utilisateur de la plateforme.
- **Oracle** : Pour la base de données utilisée pour stocker les informations.

---

### **License**

Ce projet est sous la licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

