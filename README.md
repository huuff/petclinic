[![CircleCI](https://circleci.com/gh/huuff/petclinic/tree/master.svg?style=svg)](https://circleci.com/gh/huuff/petclinic/tree/master)
[![codecov](https://codecov.io/gh/huuff/petclinic/branch/master/graph/badge.svg?token=M2KNWWF9II)](https://codecov.io/gh/huuff/petclinic)
# Spring Petclinic
* Run it with `mvn install && mvn spring-boot:run -pl petclinic-web`

## `petclinic-aggregator`
JaCoCo won't generate reports for the module that it's running it, therefore, I added an extra module that will run the reports so they are generated for all other modules.

## Lessons learned
Separating my models from my logic in two different `maven` modules locked me into [anemic domain models](https://martinfowler.com/bliki/AnemicDomainModel.html). If I wanted to have some locality putting my logic directly in the models, then I'd have some dependency in my `web` module. Since the `web` module would always depend on the `data` module, then I'd end with a circular dependency.

This was limiting, but are there any benefits to the separation that compensates it?

I think not. Maybe this allows for better distribution of the models as a library but then again:
* The models have no logic, so they aren't really that useful for any dependants
* Would I even need to distribute these? These models would be internal to the application. In any case I would want to distribute the DTOs to facilitate integration, but then, these would be generated from the OpenAPI specification. In any case, this is an api-less project, so there's nothing to get from it. (Well you could call it an API if you consider that it consumes `form-urlencoded` data and returns HTML but that's a bit farfetched)
