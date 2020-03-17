./mvnw package

docker build -f src/main/docker/Dockerfile.jvm -t quotesc-backend .

docker tag quotesc-backend:latest registry.obyoxion.at/quotesc-backend:latest

docker push registry.obyoxion.at/quotesc-backend:latest