# 📚 LibreChange — Plateforme d'échange de livres

**MGSI Groupe 45**
👩‍💻 MAHIR Rabia & 👨‍💻 ABLAD Mostapha

---

## ⚙️ Prérequis

* XAMPP (Apache + MySQL)
* PHP 7.4 ou supérieur
* Navigateur web moderne

---

## 🚀 Installation

### 1️⃣ Copier le projet

Décompresser le dossier et le placer dans :

```bash
C:\xampp\htdocs\plateforme_livres_MGSI\
```

---

### 2️⃣ Importer la base de données

1. Ouvrir : http://localhost/phpmyadmin
2. Cliquer sur **Importer**
3. Sélectionner le fichier `projet.sql`
4. Cliquer sur **Exécuter**

✔ La base `plateforme_livres` sera créée automatiquement.

---

### 3️⃣ Configurer la connexion

Modifier le fichier `config.php` si nécessaire :

```php
define('DB_HOST', 'localhost');
define('DB_NAME', 'plateforme_livres');
define('DB_USER', 'root');
define('DB_PASS', '');
```

---

### 4️⃣ Lancer l'application

```bash
http://localhost/plateforme_livres_MGSI/
```

---

## 👤 Comptes de test

### 🔐 Admin

* **Login** : ENSIASD
* **Password** : ENSIASD2026

### 🎓 Étudiants

| Login  | Password | Nom            |
| ------ | -------- | -------------- |
| mahir  | password | Rabia MAHIR    |
| ablad  | password | Mostapha ABLAD |
| benali | password | Sara Benali    |
| karim  | password | Karim Idrissi  |

---

## ⚠️ Problème mot de passe admin

Si problème de connexion :

### Solution SQL :

```sql
UPDATE utilisateurs
SET mot_de_passe = '$2y$10$YourHashHere'
WHERE login = 'ENSIASD';
```

### Ou utiliser :

```
http://localhost/plateforme_livres_MGSI/fix_admin_password.php
```

⚠️ Supprimer ce fichier après utilisation !

---

## 📁 Structure du projet

```
plateforme_livres_MGSI/
│
├── index.php
├── config.php
├── projet.sql
├── fix_admin_password.php
├── README.md
│
├── css/
│   └── style.css
│
├── js/
│   └── script.js
│
├── images/
│
├── pages/
│   ├── login.php
│   ├── register.php
│   ├── livres.php
│   ├── livre_detail.php
│   ├── ajouter_livre.php
│   ├── modifier_livre.php
│   ├── messages.php
│   ├── profil.php
│   ├── admin.php
│   └── logout.php
│
├── includes/
│   ├── header.php
│   └── footer.php
│
└── doc/
    ├── captures/
    ├── diagrammes/
    └── Fiche.pdf
```

---

## ✨ Fonctionnalités

* 📖 Publication de livres à échanger
* 🔍 Recherche avancée (titre, auteur, matière)
* 💬 Messagerie entre utilisateurs
* 👤 Profil utilisateur + bibliothèque
* ⭐ Système de notation
* 🛠️ Interface administrateur

---

## 🔒 Sécurité

* 🔐 Mots de passe hashés (bcrypt)
* 🛡️ Requêtes préparées (PDO)
* ❌ Protection XSS avec `htmlspecialchars()`
* 🔑 Gestion des sessions
* 👮 Vérification des rôles

---

## 📬 Contact

**Groupe 45 — MGSI**

* 👩‍💻 MAHIR Rabia
  📧 [rabia.mahir@ensiasd.ma](mailto:rabia.mahir@ensiasd.ma)

* 👨‍💻 ABLAD Mostapha
  📧 [ablad.m@ensiasd.ma](mailto:ablad.m@ensiasd.ma)

---
