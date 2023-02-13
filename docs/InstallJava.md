# Installing Java

## Mac

1. Install [homebrew](https://docs.brew.sh/Installation#untar-anywhere)
   1. Navigate to a directory where you wish to install homebrew
   ```sh
   cd ~/Installs/
   ```
   1. Run the install script
   ```sh
   mkdir homebrew && curl -L https://github.com/Homebrew/brew/tarball/master | tar xz --strip 1 -C homebrew
   ```
   1. Add homebrew to your path
   ```sh
   eval "$(homebrew/bin/brew shellenv)"
   brew update --force --quiet
   chmod -R go-w "$(brew --prefix)/share/zsh"
   ```
1. Install Java
   ```sh
   brew install openjdk@17
   ```
1. Update Path to include new Java version (update the command below, to point to your Java install)
   ```sh
   echo 'PATH="/Users/michaelpeels/Installs/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
   ```
