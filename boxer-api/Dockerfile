FROM openjdk:8-jre
MAINTAINER MVTM sawied danan.2009@gmail.com

VOLUME /tmp

# Add the service itself
ARG DEPENDENCY=.

# Set profile
ARG profile
ENV SPRING_PROFILES_ACTIVE=$profile

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 80

ENTRYPOINT ["java", "-cp", "app:app/lib/*","com.github.sawied.boxer.api.BoxerApiApplication"]