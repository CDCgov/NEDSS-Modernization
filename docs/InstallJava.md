# Installing Java

## Using homebrew

1. Install [Homebrew](https://docs.brew.sh/Installation#untar-anywhere)
   1. Navigate to a directory where you wish to install homebrew
      ```sh
      cd ~/Installs/
      ```
   1. Run the install script
      ```sh
      mkdir homebrew && curl -L https://github.com/Homebrew/brew/tarball/master | tar xz --strip 1 -C homebrew
      ```
   1. Update homebrew
      ```sh
      eval "$(homebrew/bin/brew shellenv)"
      brew update --force --quiet
      chmod -R go-w "$(brew --prefix)/share/zsh"
      ```
   1. Add homebrew to your PATH
      ```sh
      echo "PATH=\"$(pwd)/homebrew/bin/brew:"'$PATH'\" >> ~/.zshrc
      ```
1. Install Java
   ```sh
   brew install openjdk@17
   ```
1. Update Path to include new Java version
   ```sh
   echo "PATH=\"$(pwd)/homebrew/opt/openjdk@17/bin:"'$PATH'\" >> ~/.zshrc
   ```
