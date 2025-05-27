# 🍏 Mini Apple Store - Java E-commerce Desktop Application

Mini Apple Store is a desktop-based e-commerce application built using **Java Swing**, **SQLite**, and a modular GUI design. It allows both users and administrators to interact with a mini-store interface, simulating real-world Apple product browsing and purchase workflows.

---

## 🚀 Features

### 👤 User Side
- User registration and login
- Profile management with image
- Browse available Apple products
- Add to and remove from cart
- Order summary and checkout process
- View order history

### 🛠️ Admin Side
- Admin login with dedicated UI
- Dashboard for managing products
- Add, update, and delete items
- View user profiles and order records

### 🧰 Tools & Technologies
- SQLite for local database
- Java Swing-based modern UI
- JDBC for database connectivity
- Utility methods for database and image handling

---

## 📁 Project Structure

```bash
Mini_Apple_Store/
│
├── *.java                 # Java source files for UI and logic
├── db/database.db         # SQLite database
├── *.jar                  # Required libraries (e.g., JavaMail)
├── resources/             # Images and user profile pictures
└── build&run.bat          # Script to compile and launch the application on Windows
```
## 📝 Setup Instructions
Requirements:
- Java JDK 8 or later

- SQLite JDBC driver (included)

- Java-supported IDE (IntelliJ, Eclipse, NetBeans)

**How to Run**
Clone or unzip the project.

Use the provided **build&run.bat** (Windows) or compile manually:

```bash

javac *.java
java Main
```

