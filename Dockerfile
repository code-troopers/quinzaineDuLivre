FROM jdubois/jhipster-docker
MAINTAINER m.bollot@code-troopers.com

USER jhipster
WORKDIR /app
CMD ["mvn", "-Pprod", "spring-boot:run"]
