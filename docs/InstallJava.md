# Installing Java

## Using homebrew

1. Install [Homebrew](./InstallHomebrew.md) if you do not have it already.
1. Install Java
   ```sh
   brew install openjdk@17
   ```
1. Update Path to include new Java version
   ```sh
   echo "PATH=\"$(pwd)/homebrew/opt/openjdk@17/bin:"'$PATH'\" >> ~/.zshrc
   ```
