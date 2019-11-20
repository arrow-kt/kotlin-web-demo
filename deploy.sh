  
#!/usr/bin/env bash

cd try.arrow-kt.io

git fetch --prune

git checkout master

git pull

cp ~/cert/* -r docker/frontend/conf

sh gradlew clean

sh gradlew ::copyKotlinLibs

sh gradlew war

sudo docker-compose down

sudo docker system prune -a -f

sudo docker volume rm $(sudo docker volume ls -qf dangling=true)

sudo docker-compose up -d

