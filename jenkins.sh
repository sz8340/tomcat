<<<<<<< HEAD
# REMOVING OLD IMAGES, CONTIANERS, TAGS
=======
i# REMOVING OLD IMAGES, CONTIANERS, TAGS
>>>>>>> c19a77bd89e933e8527af19094b587913391b67c
#docker stop mysql1 tomcat1
#docker rm mysql1 tomcat1
docker service rm tomcat1 || true
docker service rm mysql1 || true
docker rmi mysql:5.7 -f || true
docker rmi tomcat:8.5.24 -f || true
<<<<<<< HEAD
docker rmi 10.10.1.11/fda-org/fda-repo:tomcat1 -f || true
docker rmi 10.10.1.11/fda-org/fda-repo:mysql1 -f || true
docker network rm network67 || true
docker network rm samir_default || true
docker stack rm samir || true
DTR_ID_ADMIN=admin
DTR_PASS_ADMIN=vodopad7

# SETTING ENVIRONMENT"
#export CLASSPATH="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64/lib/tools.jar:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64/jre/lib/rt.jar:/opt/apache-tomcat-8.5.24/lib/servlet-api.jar:/usr/share/java/mysql-connector-java.jar"
export CLASSPATH="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64/lib/tools.jar:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64/jre/lib/rt.jar:/home/sz8340/fda/WEB-INF/lib/mysql-connector-java.jar:/home/sz8340/fda/WEB-INF/lib/tomcat-servlet-api-7.0.6.jar"
=======
docker rmi 18.217.122.146/fda-org/fda-repo:tomcat1 -f || true
docker rmi 18.217.122.146/fda-org/fda-repo:mysql1 -f || true
docker network rm network67 || true
docker network rm samir_default || true
docker stack rm samir || true

DTR_ID_ADMIN="admin"
DTR_PASS_ADMIN="vodopad7"
# SETTING ENVIRONMENT"
#export CLASSPATH="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64/lib/tools.jar:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64/jre/lib/rt.jar:/opt/apache-tomcat-8.5.24/lib/servlet-api.jar:/usr/share/java/mysql-connector-java.jar"
export CLASSPATH="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64/lib/tools.jar:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64/jre/lib/rt.jar:/home/ec2-user/fda/WEB-INF/lib/mysql-connector-java.jar:/home/ec2-user/fda/WEB-INF/lib/tomcat-servlet-api-7.0.6.jar"
>>>>>>> c19a77bd89e933e8527af19094b587913391b67c

# EXECUTING BUILD
ant clean war

# CREATING TOMCAT DOCKER IMAGE
cp dist/fda.war tomcat
cd tomcat
docker build -t tomcat:8.5.24 .

# CREATING MYSQL DOCKER IMAGE
cd ../mysql
docker build -t mysql:5.7 .
cd ..

# CREATING PRIVATE NETWORK   (to support network over several hosts driver must be "overlay", default is bridge)
# docker network create network67 -d overlay

# PUSH DOCKER IMAGE TO DTR
<<<<<<< HEAD
docker login -u ${DTR_ID_ADMIN} -p ${DTR_PASS_ADMIN} 10.10.1.11

# TAG DOCKER IMAGES
docker tag tomcat:8.5.24 10.10.1.11/fda-org/fda-repo:tomcat1
docker tag mysql:5.7 10.10.1.11/fda-org/fda-repo:mysql1
 
# PUSH DOCKER IMAGES TO DTR 
docker push 10.10.1.11/fda-org/fda-repo:tomcat1
docker push 10.10.1.11/fda-org/fda-repo:mysql1
=======
docker login -u ${DTR_ID_ADMIN} -p ${DTR_PASS_ADMIN} 18.217.122.146

# TAG DOCKER IMAGES
docker tag tomcat:8.5.24 18.217.122.146/fda-org/fda-repo:tomcat1
docker tag mysql:5.7 18.217.122.146/fda-org/fda-repo:mysql1
 
# PUSH DOCKER IMAGES TO DTR 
docker push 18.217.122.146/fda-org/fda-repo:tomcat1
docker push 18.217.122.146/fda-org/fda-repo:mysql1
>>>>>>> c19a77bd89e933e8527af19094b587913391b67c

# CREATING TOMCAT/MYSQL DOCKER CONTAINERS (only if running on one host)
#docker run --network=network67 -itd -p 8083:8080 --name tomcat1 tomcat:8.5.24
#docker run --network=network67 -itd --name mysql1 mysql:5.7

# CREATE SERVICE OF 3 REPLICAS OF TOMCAT/MYSQL CONTAINERS (if running on mmulti host)
# IMPORTANT: name mysql1, because java program is calling hostname mysql1
<<<<<<< HEAD
# docker service create --replicas 3 --network=network67 -p8084:8080 -td --name tomcat1 10.10.1.11/fda-org/fda-repo:tomcat1
# docker service create --replicas 1 --network=network67 -td --name mysql1 10.10.1.11/fda-org/fda-repo:mysql1
=======
# docker service create --replicas 3 --network=network67 -p8084:8080 -td --name tomcat1 18.217.122.146/fda-org/fda-repo:tomcat1
# docker service create --replicas 1 --network=network67 -td --name mysql1 18.217.122.146/fda-org/fda-repo:mysql1
>>>>>>> c19a77bd89e933e8527af19094b587913391b67c

# CREATE A STACK
docker stack deploy -c docker-compose.yml samir

# LIST AVAILABLE STACKS
docker stack ls
docker stack ps samir

# LIST DOCKER SERVICES
docker service ls

# NETWORK INSPECT (to see that both services is part of the network)
docker inspect network67 || true
docker inspect samir_default || true

# LOGS - MYSQL1
#docker logs ysql2.2.kkplqtv5mh40w6t3se3gb9ko9

#start tomcat container only
#docker exec -it tomcat1.1.oi632zpd3c4fl14dnm6x97cdq bash
#docker exec -it mysql2.2.kkplqtv5mh40w6t3se3gb9ko9 mysql -uroot -proot -e"show databases"

#import fda.sql and create new user
#docker exec -it mysql1 bash
#mysql -u root -proot -e "create database fda"
#mysql -u root -proot fda < /tmp/fda.sql
#mysql -u root -proot
#create user 'Dude1'@'%' identidied by 'SuperSecret7!';
#grant all on *.* to 'Dude1'@'%' with grant option;
#flush privileges;


