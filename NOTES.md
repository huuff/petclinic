## Getting embedded MongoDB working on NixOS
Seems like it relies on dynamic linking to FHS directories so:
* Install `mongod`
* Symlink the binary to `~/.embedmongo/extracted/Linux-B64--3.5.5/extractmongod`
* Therefore: `ln -s $(readlink $(which mongod)) ~/.embedmongo/extracted/Linux-B64--3.5.5/extractmongod`

Thus, we're using our installed mongod instead of the embedded one.

## Random idea warnings
* `Value is never used as publisher` in the controllers. What does this mean? Surely sending them to thymeleaf counts as using them as publishers?
