# gidel-jwt-core
Minimal, modular JWT utilities and services for key loading, registry management and token operations.

- Purpose - Provide utilities to load PEM keys, register key material, and produce/verify JWTs via a simple service API.

- Maven coordinates Use the library from Maven Central with these coordinates:

```xml
<dependency>
  <groupId>io.github.tmbxgidel</groupId>
  <artifactId>gidel-jwt-core</artifactId>
  <version>1.0.0-alpha</version>
</dependency>
```

Build and test - Build the project with Maven:
```bash
mvn clean install
```
Quick usage overview - Load keys: - Use PemKeyLoader to load a single PEM or KeyDirectoryLoader to load a directory of key files. - Register keys: - Register loaded JwtKeyMaterial instances with JwtKeyRegistry / JwtRegistryManager. - Use service: - Obtain a GidelJwtService implementation (default provided by GidelDefaultJwtServiceImpl) to create and validate JWTs.

API summary - Packages and notable classes: - enums/KeyType.java — key type enum. - enums/PurposeType.java — purpose enum (signing, encryption, etc.). - service/GidelJwtService.java — public service interface for JWT operations. - serviceImp/GidelDefaultJwtServiceImpl.java — default service implementation. - serviceImp/KeyDirectoryLoader.java — load keys from a directory. - serviceImp/PemKeyLoader.java — load a PEM key file. - utils/JwtKeyMaterial.java — key material holder. - utils/JwtKeyRegistry.java — in-memory registry for keys. - utils/JwtRegistryManager.java — registry manager/helper.

Examples - Example build snippet shown above. Implementation usage depends on application needs; typical flow is: - load keys > register keys > call GidelJwtService to sign/verify tokens.

Contributing - Contributions accepted via pull requests. Follow existing code style and tests.

License - Apache License, Version 2.0 (see pom.xml).

Project - Repository: https://github.com/TMBxGIDEL/gidel-jwt-core