const report = require("multiple-cucumber-html-reporter");
report.generate({
  jsonDir: "./reports/", // ** Path of .json file **//
  reportPath: "./reports/cucumber-report",
  openReportInBrowser: true,
  metadata: {
    browser: {
      name: "chrome",
      version: "XX",
    },
    device: "Local test machine",
    platform: {
      name: "osx",
      version: "13.31",
    },
  },
});
