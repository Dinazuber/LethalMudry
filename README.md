# LethalMudry 🎮

Un jeu 2D développé en **Scala** avec la bibliothèque graphique [`gdx2d`](https://isc-hei.github.io/gdx2d/), conçu pour tourner sur desktop (Windows, Linux, macOS).
Le jeux se base sur sa version 3D LethalCompany avec des adaptations à la filières Informatique et système de communication.
---

## 📋 Prérequis

- **IntelliJ IDEA**
- **Scala** installé et configuré dans l'IDE

---

## 🚀 Installation & Lancement

### Via IntelliJ IDEA

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/Dinazuber/LethalMudry.git
   ```
2. Ouvrir le projet dans **IntelliJ IDEA** (`File > Open`).
3. S'assurer que le SDK **graalvm-ce-17** est configuré (`File > Project Structure`).
4. Lancer la classe principale depuis l'IDE.

---

## 📁 Structure du projet

```
LethalMudry/
├── data/
│   └── images/         # Assets graphiques du jeu
│   └── music/          # Assets audio du jeu
│   └── styles/         # Assets style du jeu
│       └── healthBar/  # healthBar Bar Style
│       └── inventory/  # inventory Bar Style
│       └── label/      # label Bar Style
│       └── ligthBar/   # ligthbar Bar Style
├── libs/               # Bibliothèque gdx2d-desktop v1.2.1
├── src/
│   └── lethalmudry/    # Code source Scala du jeu
│       └── enemies/    # Enemies logic
│       └── ui/         # Menu & Death screen logic
│   └── objects/        # Objects logic
├── .classpath
├── .project
├── gdx2d-lethalmudry.iml
└── README.md
```

---

## 🛠️ Dépendances

| Bibliothèque | Version | Description |
|---|---------|---|
| `gdx2d-desktop` | `1.2.2` | Framework graphique 2D multiplateforme |

La bibliothèque `gdx2d` est incluse directement dans le dossier `libs/`. Les sources sont disponibles dans le fichier Jar. La Javadoc complète est consultable [ici](https://hevs-isi.github.io/gdx2d/javadoc/).

---

## 📚 À propos de gdx2d

[`gdx2d`](https://isc-hei.github.io/gdx2d/) est un framework graphique 2D simple d'utilisation, développé à la HES-SO Valais. Il offre :

- Primitives graphiques (lignes, cercles, rectangles, images)
- Transformations (rotation, échelle)
- Simulation physique via **Box2D**
- Gestion du son et de la musique
- Support multiplateforme (Windows, Linux, macOS)

---

## 👤 Auteurs

- **Dina Zuber** — développement principal
- **Noah Abraão** - développement principal

---

## 🎥 Gameplay

Voici une vidéo de 15 secondes résumant notre jeu :
https://github.com/user-attachments/assets/3ca71ca4-7e1e-4469-8985-fab75fba5f92
