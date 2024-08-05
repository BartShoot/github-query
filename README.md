# Requirements

- Java 21
- Maven(Optional)

# Launching application

[//]: # (TODO: add launch parameters - port, ip etc)

## Windows:

```powershell
./mvnw.cmd spring-boot:run
```

## Linux:

```bash
.\mvnw spring-boot:run 
```

# Endpoints

Default address is `localhost:8080`

`GET /user/{userName}/repos` - returns list of non-forked repos with

[//]: # (TODO: add rest)

[//]: # (Add example response)

# Configuration

If you find yourself going against GitHub API rate limit rename `src/main/resources/application.properties_TEMPLATE`
to `src/main/resources/application.properties` and fill `github.api-key` with your
own [Personal access token.](https://github.com/settings/tokens)