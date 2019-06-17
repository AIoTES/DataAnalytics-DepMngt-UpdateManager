FROM tomcat:alpine

ENV DOCKER="True"
ENV DOCKER_REGISTRY_SERVER_URL="https://docker-activage.satrd.es"
ENV DOCKER_REGISTRY_USER="activage"
ENV DOCKER_REGISTRY_PASSWORD="docker.activage"
ENV STORAGE="IN_MEMORY"
ENV POSTGRES_HOST=localhost
ENV POSTGRES_PORT=5432
ENV POSTGRES_DATABASE=activage-update-manager
ENV POSTGRES_USER=activage
ENV POSTGRES_PASSWORD=activage


RUN rm -rf /usr/local/tomcat/webapps/*
ADD target/update-manager.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
