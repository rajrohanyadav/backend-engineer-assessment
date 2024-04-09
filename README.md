# Backend Engineer Assignment

**IMPORTANT: Do not send pull requests to this repository. This is a template repository and is not used for grading. Any pull requests will be closed and ignored.**

**Assignment status: I wasn't able to get the temporal to connect to the app running from `compose.yml` in time. Therefore, leaving that code as commented out.**

## Tasks:
- [x] Important: Fork the above repo
- [x] Stripe Integration for Customer Creation using Temporal Workflow
- [x] Add the following fields in the APIs
- [x] Add a new API to update fields in the API
- [ ] Add a Dockerfile - code commented out
- [x] API Implementation
- [ ] BONUS: Writing Tests - integrated but no tests could be completed
- [x] Code Walkthrough

To run the application, you'll need to use the same local method:
- Temporal cli running with `temporal server start-dev`
- Run app with `./gradlew bootRun`
- `api.http` can be used to test if needed.
- Redacting my stripe API Key 😊.

## Introduction

If you are reading this, you are probably have received this project as a coding challenge. Please read the instructions
carefully and follow the steps below to get started.

## Setup

### Pre-requisities

To run the application you would require:

- [Java](https://www.azul.com/downloads/#zulu)
- [Temporal](https://docs.temporal.io/cli#install)
- [Docker](https://docs.docker.com/get-docker/)
- [Stripe API Keys](https://stripe.com/docs/keys)

### On macOS:

First, you need to install Java 21 or later. You can download it from [Azul](https://www.azul.com/downloads/#zulu) or
use [SDKMAN](https://sdkman.io/).

```sh
brew install --cask zulu21
```

You can install Temporal using Homebrew

```sh
brew install temporal
```

or visit [Temporal Installation](https://docs.temporal.io/cli#install) for more information.

You can install Docker using Homebrew

```sh
brew install docker
```

or visit [Docker Installation](https://docs.docker.com/get-docker/) for more information.

### Other platforms

Please check the official documentation for the installation of Java, Temporal, and Docker for your platform.

### Stripe API Keys

Sign up for a Stripe account and get your API keys from the [Stripe Dashboard](https://dashboard.stripe.com/apikeys).
Then in `application.properties` file add the following line with your secret key.

```properties
stripe.api-key=sk_test_51J3j
```

## Run

You are required to first start the temporal server using the following command

```sh
temporal server start-dev
```

and then run the application using the following command or using your IDE.

```sh
./gradlew bootRun
```

### Other commands

#### Test
To run tests, use the following command

```sh
./gradlew test
```

#### Lint
To run lint checks, use the following command

```sh
./gradlew sonarlintMain
```

#### Code Formatting
To format the code, use the following command

```sh
./gradlew spotlessApply
```

#### Local Development
To run the local development server, use the following command
```sh
docker compose up -d
```
To stop the server, use the following command
```sh
docker compose down
```

## Guides

The following guides illustrate how to use some features concretely:

- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Temporal Quick Start](https://docs.temporal.io/docs/quick-start)
- [Temporal Java SDK Quick Guide](https://docs.temporal.io/dev-guide/java)
- [Stripe Quick Start](https://stripe.com/docs/quickstart)
- [Stripe Java SDK](https://stripe.com/docs/api/java)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

- postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.
