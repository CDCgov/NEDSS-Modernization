import { faker } from "@faker-js/faker";

class UtilityFunctions {
  getRandomLetter() {
    const letters = 'abcdefghijklmnopqrstuvwxyz';
    const randomIndex = Math.floor(Math.random() * letters.length);
    return letters[randomIndex];
  }

  generateRandomSSN() {
    return Array(9).fill().map(() => faker.number.int(9)).join('');
  }

  generateTimestamp() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    return `${year}${month}${day}${hours}${minutes}`;
  }

  capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

  generateRandomLastName() {
    let randomLastName = faker.person.lastName().toLowerCase() + this.getRandomLetter() + this.getRandomLetter() + this.getRandomLetter() + this.getRandomLetter() + this.getRandomLetter();
    randomLastName = this.capitalizeFirstLetter(randomLastName).replace(/[^0-9a-z]/gi, '');
    return randomLastName;
  }

  formatSSN(ssn) {
    const ssnString = ssn.toString();
    return `${ssnString.slice(0, 3)}-${ssnString.slice(3, 5)}-${ssnString.slice(5)}`;
  }

  replacePlaceholders(message, replacements) {
    return Object.entries(replacements).reduce((modifiedMessage, [placeholder, replacement]) => {
      return modifiedMessage.replaceAll(placeholder, replacement);
    }, message);
  }
}

export default new UtilityFunctions();
