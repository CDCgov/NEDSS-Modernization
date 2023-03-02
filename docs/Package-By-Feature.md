# Package By Feature

Dividing the codebase by business functionality placing all classes required for a feature in the same package.

## Advantages

- Informative structure that makes it easier to find features in the codebase.
- High cohesion between components within a package.
- Low coupling between packages.
- High modularity where each feature is isolated from another.

## What is a Feature?

Any high-level aspect of the domain can be a feature. For example, these packages could exist in `modernization-api`;

- `gov.cdc.nbs.user`
- `gov.cdc.nbs.encryption`
- `gov.cdc.nbs.investigation`
- `gov.cdc.nbs.lab`
- `gov.cdc.nbs.patient`
- `gov.cdc.nbs.patient.create`
- `gov.cdc.nbs.patient.merge`
- `gov.cdc.nbs.patient.search`
- `gov.cdc.nbs.patient.treatment`

This example also shows that a high-level feature can be further broken down into distinct features. An in code example
can be found in the `modernization-api` project where the `gov.cdc.nbs.patient.treatment` package was created for the
Patient Treatment graphql resolver.

## Best Practices

- Group code by the use case being represented. This will result in self-contained pieces of functionality that are easy
  to change.
- Now that your code is all in one spot exercise those access modifiers to enforce boundaries between packages. Only
  make `public` what needs to be. The fewer dependencies makes code easier to change.
- Be aware of coupling between features. The more tied together features are the harder they are to change
  independently.
- Make cross-cutting concerns that are typically common or util code into separate features.

## Next Steps

Moving from Package by Layer to Package by Feature can be a huge disruption to work in progress. As new development
occurs create the feature package and move any required classes into it. Don't be afraid to move things!

## Notes

Packaging by feature is not only a backend packaging concept. Storybook files can be included with the components they
are describing.