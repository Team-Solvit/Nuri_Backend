./gradlew bootJar
docker build -t dwk48/nuri .
docker push dwk48/nuri
docker login -u dwk48 -p solvit!!abc
docker pull dwk48/nuri
docker-compose down --volumes
# shellcheck disable=SC2046
kill $(lsof -t -i:9092)
docker-compose --env-file env/docker/local.env up -d