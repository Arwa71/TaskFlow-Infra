# TaskFlow (reference rebuild)

Task management REST API: Spring Boot 3, Java 17, PostgreSQL, Redis,
Docker, Nginx (SSL), Prometheus, Grafana, GitHub Actions.

## Run locally
```bash
cp .env.example .env              # edit the passwords
mkdir -p nginx/certs
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx/certs/privkey.pem -out nginx/certs/fullchain.pem \
  -subj "/CN=localhost"
docker compose up -d --build
```

## Test it
```bash
curl -k -X POST https://localhost/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Docker","description":"multi-stage builds"}'
curl -k https://localhost/api/tasks
curl -k https://localhost/api/tasks/1
```
Health check (from inside the server): `docker compose exec app wget -qO- localhost:8080/actuator/health`

Grafana: http://localhost:3000 -> add data source Prometheus `http://prometheus:9090`.

## GitHub Secrets needed for deployment
VPS_HOST, VPS_USER, VPS_SSH_KEY

## Server setup (once)
Install Docker, create ~/taskflow with docker-compose.yml, nginx/, prometheus/ and .env.
