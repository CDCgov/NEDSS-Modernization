# nbs-classic

An NBS6 container to use for local development, research and testing.

## Building

The `build.sh` script automates the setup of the `wildfly` container by downloading the contents of [NEDSSDev](https://github.com/cdcent/NEDSSDev), building the application from a specific branch, and then containerizing a WildFly server with an NBS6 deployment.

If the specified path does not exist, the script will clone the repository and clean up the directory after building.

**Usage:**

```sh
./build.sh -p /builder/NEDSSDev/ -v NBS_6.0.18
```

| Option | Default              | Description                                                                        |
| ------ | -------------------- | ---------------------------------------------------------------------------------- |
| `-p`   | `/builder/NEDSSDev/` | The path to the NBS6 code base from [NEDSSDev](https://github.com/cdcent/NEDSSDev) |
| `-v`   | `NBS_6.0.18`         | Branch or tag of NEDSSDev to build from.                                           |

## Configuration

By default, the `wildfly` container connects to the `nbs-mssql` development database container.  
You can adjust connection settings in the Docker Compose configuration or environment files as needed.

## Notes

- Ensure Docker and Docker Compose are installed and running.
- The script requires access to the NEDSSDev repository (SSH or HTTPS).
- For custom builds, specify a different path or branch using the `-p` and `-v` options.
