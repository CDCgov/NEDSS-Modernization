# Code Formatting

## Java Formatting

Java Code is formatted using the Google Java Style Guide with minimal customizations.

### IntelliJ

1. Open settings -> Editor -> Code Style
1. Click the Gear icon next to the Scheme name
1. Select Import Scheme -> Eclipse XML Profile
1. Select the [eclipse-java-google-style.xml](../eclipse-java-google-style.xml) file in the root of the repository

### VS-Code

1. Add the following to your `settings.json`.

   ```json
   "java.format.settings.profile": "GoogleStyle",
   "java.format.settings.url": "eclipse-java-google-style.xml"
   ```

### Customizations

1. Allow method parameters to be on new lines

   ```xml
   <setting id="org.eclipse.jdt.core.formatter.join_wrapped_lines" value="false"/>
   ```

   Example:

   ```java
   return new PatientCommand.AddPatient(
               request.patient(),
               request.patientLocalId(),
               request.ssn(),
               request.dateOfBirth(),
               request.birthGender(),
               request.currentGender(),
               request.deceased(),
               request.deceasedTime(),
               request.maritalStatus(),
               request.ethnicity(),
               request.asOf(),
               request.comments(),
               request.createdBy(),
               request.createdAt());
   ```

1. Increase max line length to 120

   ```xml
   <setting id="org.eclipse.jdt.core.formatter.lineSplit" value="120"/>

   ```

## Typescript Formatting

Typescript formatting is handled by the [Prettier](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode) plugin.
