# Build
mvn clean install -DskipTests

# Manual step for new before docker build
# To change from fat jar to separate layers
# TODO: add this to the build goal
mkdir -p target/dependency && (cd target/dependency; jar -xf *.jar)

# Docker build
docker build -t todo:1 -f docker/Dockerfile .

# Run the service
docker run -p 5555:5555 -e spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/todo todo:1

# Generate k8s todo deployment yaml
kubectl run todo --image=todo:1 --dry-run -o yaml >todo.yaml
# Specify container port in yaml

        ports:
        - containerPort: 5555

# Generate k8s postgres deployment yaml
# On k8s worker nodes:
# docker pull postgres
# kubectl run postgres --image=postgres --dry-run -o yaml >postgres.yaml 
# Specify container port in yaml

        ports:
        - containerPort: 5432

# Add the following env var under container. This sets the specified password for postgres user

        env:
        - name: POSTGRES_PASSWORD
          value: password

# Create todo db in postgres container
k exec -it postgres-57597d5d9-sln96 bash

su postgres
psql
CREATE DATABASE todo;
grant all privileges on database todo to postgres;

