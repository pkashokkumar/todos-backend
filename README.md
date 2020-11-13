# Build
mvn clean install -DskipTests

# Run
java -jar target/todo-0.0.1-SNAPSHOT.jar

# --------- Docker build and run -------

# Manual step for now before docker build
# To change from fat jar to separate layers
# TODO: add this to the build goal
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Docker build
docker build -t todo:1 -f build/Dockerfile .

# Run the service with local postgres
docker run -p 5555:5555 -e spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/todo todo:1

# Run the service with RDS
docker run -p 5555:5555 -e spring.datasource.url=jdbc:postgresql://akprojects.c319dc9124df.us-west-1.rds.amazonaws.com:5432/todo todo:1


# ----------- Run in k8s ---------------

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


# ------ Set up postgres db --------

# Create todo db in postgres container
k exec -it postgres-57597d5d9-sln96 bash

# if running postgres as container todo-db in k8s
su postgres
psql
CREATE DATABASE todo;
grant all privileges on database todo to postgres;

# when using local postgres or rds
create user todo
alter user todo with password 'password';
grant all privileges on database todo to todo;

