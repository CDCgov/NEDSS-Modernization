const report = require("multiple-cucumber-html-reporter");
const fs = require("fs");
const dayjs = require("dayjs");

const runInfo = JSON.parse(fs.readFileSync("results.json", "utf8"));

const getOSName = () => {
  return (
    {
      darwin: "osx",
      win32: "windows",
      ubuntu: "ubuntu",
    }[runInfo.osName] ||
    runInfo.osName ||
    "unknown"
  );
};

const getBrowser = () => {
  return (
    {
      chrome: "chrome",
      electron: "chrome",
      firefox: "firefox",
    }[runInfo.browserName] ||
    runInfo.browserName ||
    "unknown"
  );
};

report.generate({
  jsonDir: "./reports/", // ** Path of .json file **//
  reportPath: "./reports/cucumber-report",
  openReportInBrowser: false,
  metadata: {
    browser: {
      name: getBrowser(),
      version: runInfo.browserVersion,
    },
    device: "GitHub Action - ubuntu",
    platform: {
      name: getOSName(),
      version: runInfo.osVersion,
    },
  },
  customData: {
    title: "Run Info",
    data: [
      { label: "Cypress Version", value: runInfo["cypressVersion"] },
      { label: "Node Version", value: runInfo["nodeVersion"] },
      {
        label: "Execution Start Time",
        value: dayjs(runInfo["startedTestsAt"]).format(
          "YYYY-MM-DD HH:mm:ss.SSS"
        ),
      },
      {
        label: "Execution End Time",
        value: dayjs(runInfo["endedTestsAt"]).format("YYYY-MM-DD HH:mm:ss.SSS"),
      },
    ],
  },
});
