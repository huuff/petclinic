#!/usr/bin/env bash

mvn install && mvn spring-boot:run -pl petclinic-web "$1"
