# CodingPath

An AI-powered coding guidance desktop app that helps students figure out what to learn next — built with Java Swing and backed by an Oracle database.

## Overview

CodingPath is a programming learning hub. New users sign up and go through a short onboarding flow (field of study + experience level), then land straight in an AI-powered "Coding Guide" chat screen where they can ask for personalized learning direction.

## Features

- **Secure Authentication** — Login/signup with SHA-256 hashed passwords
- **Personalized Onboarding** — Captures field of study and experience level to tailor guidance
- **AI Coding Guide** — Chat-based assistant powered by an LLM for programming advice and learning paths
- **Dark Theme UI** — Consistent dark theme across all screens (colors/fonts centralized in `Theme.java`)
- **Oracle Database Integration** — User data and app state persisted via Oracle DB

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Java Swing |
| Database | Oracle Database (SQL Developer) |
| AI | Groq API (`llama-3.3-70b-versatile`) |
| IDE | IntelliJ IDEA Ultimate |

## Project Structure

```
CodingPath/
├── assets/              # Background images and UI assets
├── src/
│   ├── Main.java
│   └── codepath/
│       ├── AIChatScreen.java       # Coding Guide chat UI
│       ├── AIChatService.java      # Groq API integration
│       ├── AuthManager.java        # Login/signup, password hashing
│       ├── DBConnection.java       # Oracle DB connection
│       ├── GuidanceEngine.java     # Core recommendation logic
│       ├── LoginScreen.java
│       ├── OnboardingScreen.java
│       ├── ProfileScreen.java
│       ├── Theme.java              # Shared dark theme colors/fonts
│       └── ...
└── .gitignore
```

## Getting Started

### Prerequisites
- Java JDK installed
- Oracle Database (with SQL Developer or similar)
- A Groq API key ([console.groq.com](https://console.groq.com))

### Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/noor-ul-subha/CodingPath.git
   ```
2. Add your Groq API key in `openai.key/key.txt` (this file is gitignored and won't be tracked).
3. Configure your Oracle DB connection details in `DBConnection.java`.
4. Open the project in IntelliJ IDEA and run `Main.java`.

## Screenshots

! [Login Screen] (screenshots/login.png)

! [Onboarding Screen] (screenshots/onboarding.png)

! [Chat Screen] (screenshots/chat.png)

## Author

**Noor Ul Subha**
BS Software Engineering, COMSATS University Islamabad — Sahiwal Campus
