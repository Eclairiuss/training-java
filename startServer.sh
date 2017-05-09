
docker network create my_network

docker run --network my_network --name mysql-run -v $PWD/initDB:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD="root" -d mysql
docker run --network my_network -d --name tomcat-run -v $PWD/target/war:/usr/local/tomcat/webapps -p 127.0.0.1:8080:8080 -p 127.0.0.1:8000:8000 tomcat:8.0.43-jre8
