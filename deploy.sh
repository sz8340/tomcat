ant clean war
chmod 755 dist/fda.war
docker cp dist/fda.war bb3:/u01
docker cp deploy.py bb3:/tmp
docker exec -it bb3 /bin/bash -c "wlst /tmp/deploy.py"

