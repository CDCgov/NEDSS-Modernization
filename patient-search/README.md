# About

NEDSS-Modernization

[API Information](api/README.md) <br>
[UI Information](ui/README.md)

## Packaging

The Gradle build is configured to use the [com.moowork.node](https://plugins.gradle.org/plugin/com.moowork.node) plugin to package the React application into the Jar file.

## Storybook

Storybook is a component library thats gives inforation on how to use the reusable react components like button,checkbox, table and so on. Run storybook locally to see what components are already available.

```sh
ESLINT_NO_DEV_ERRORS=true npm run storybook.
```
The no lint option is to ensure there aren't any lint errors as of now. This could be an effort going forward. 
