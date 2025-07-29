# Smartlocker - Running Docker

Este projeto tem como objetivo o desenvolvimento de um cacifo inteligente para a troca de objetos entre indivíduos.

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [File Structure](#file-structure)
3. [Running the Application](#running-the-application)

---

## Prerequisites

- Docker and Docker Compose installed
- Your SQL scripts in folder `sql`
- Java application built into a JAR under `app/`

---
## Building .jar application
Please verify that all ports are correct before compile.

Compile Instruction:

```
gradlew build 
```

---
## File Structure

Your project directory should look like:

```
.
├── Dockerfile
├── docker-compose.yml
├── app
│   └── *.jar
└── sql/
    ├── create-db.sql
    ├── user/
    │   ├── create.sql
    │   ├── insert.sql
    │   └── create-user-trigger.sql
    ├── friends/
    │   ├── create.sql
    │   ├── insert.sql
    │   ├── insert-friend-trigger.sql
    │   └── update-friend-trigger.sql
    ├── module/
    │   ├── create.sql
    │   ├── insert.sql
    │   └── insert-module-trigger.sql
    ├── locker/
    │   ├── create.sql
    │   ├── insert.sql
    │   └── insert-locker-trigger.sql
    ├── trade/
    │   ├── create.sql
    │   ├── insert.sql
    │   ├── insert-trade-trigger.sql
    │   └── update-friend-trigger.sql
    └── hash/
        ├── create.sql
        └── insert.sql
```

---

## Running the Application

1. Check that the port mappings in `docker-compose.yml` are correct: 
   ```
   services:
   app:
    ...
    ports:
      - "8091:8080"

   db:
    ...
    ports:
      - "5430:5432"
    ...
   ```
   
2. Start via Docker Compose:

   ```bash
   docker-compose up --build
   ```

3. On first launch, you’ll see logs in the `db` container showing:

   - Creation of the `postgres` database
   - Execution of all SQL scripts

4. Your Database will be available at `http://localhost:5430`.

    and
    
    Your Spring Boot app will be available at `http://localhost:8091`.


