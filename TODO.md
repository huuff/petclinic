# Tasks
* At least 70% coverage
* Use slugs for outgoing properties?
* Building links in views with static methods like `OwnerController.redirectToOwnerView` (Or something similar)
* What about updates (merging only properties that aren't null) directly in the models? This kills the anemic models antipattern
* Add RememberMe, use full authentication for CUD
* See if I can use `mapstruct` for form converters
* My own login form
* Show different colors for visits in accordion depending on whether they are today, upcoming or passed.
* Allow option for a vet to add a comment to a visit
* Request vets to comment all past visits
* Notify vets of newly appointed visits
* Some way of telling an owner he doesn't have any pets and to add one
* Block login for an account on 3 password failures
* It really looks like visits should be called appointments
* An awful lot of my accesses to repositories are followed by checking whether the object is present and throwing an execption if not. This is getting really old. Encapsulate them in services.
* Selector fragment
