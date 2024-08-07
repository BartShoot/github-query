# Requirements

- Java 21
- Maven(Optional)

# Configuration

Copy and rename `src/main/resources/authentication.properties_TEMPLATE`
to `src/main/resources/authentication.properties`.

If you find yourself going against GitHub API rate limit fill `github.api-key` with your
own [Personal access token.](https://github.com/settings/tokens)

# Launching application

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

`GET /user/{userName}/repos` - returns list of all repos that aren't forks with all branches and their latest commit sha.

Example: 

`GET: http://localhost:8080/user/bartshoot/repos`

Response:
```json
[
  {
    "repository_name": "NoodleCV",
    "owner_login": "BartShoot",
    "branches": [
      {
        "name": "BartShoot-patch-1",
        "last_commit_sha": "32aab55970bb43e018cbf2f3762b7bb1e8969391"
      },
      {
        "name": "main",
        "last_commit_sha": "e7795ad9cdf685e2183aa50a71c7347cb4221abf"
      }
    ]
  },
  {
    "repository_name": "Porownywanie-Obrazow",
    "owner_login": "BartShoot",
    "branches": [
      {
        "name": "WPF",
        "last_commit_sha": "ad85d53efdcd5eb90181b10870134e3b57183997"
      },
      {
        "name": "master",
        "last_commit_sha": "350fd87fe99161460bb48da8e767a55944bd7fdf"
      }
    ]
  },
  ...
]
```
