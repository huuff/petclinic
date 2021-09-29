[![CircleCI](https://circleci.com/gh/huuff/petclinic/tree/master.svg?style=svg)](https://circleci.com/gh/huuff/petclinic/tree/master)
[![codecov](https://codecov.io/gh/huuff/petclinic/branch/master/graph/badge.svg?token=M2KNWWF9II)](https://codecov.io/gh/huuff/petclinic)
# Spring Petclinic
* Run it with `mvn install && mvn spring-boot:run -pl petclinic-web`

## `petclinic-aggregator`
JaCoCo won't generate reports for the module that it's running it, therefore, I added an extra module that will run the reports so they are generated for all other modules.
