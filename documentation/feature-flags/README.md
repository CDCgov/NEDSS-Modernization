# Feature flagging

Features under development for the modernized NBS application should be accessible for quality assurance to determine
when the feature meets the criteria to be included in a release. Feature flags are a powerful technique that allows
specific features to be turned on or off without needed to re-package code.

## Key benefits:

- Reduced Risk: They allow developers to deploy new code to production environments safely, even if it's not fully
  ready. If issues arise, the feature can be quickly turned off without affecting other parts of the application.
- Faster Releases: Feature flags enable continuous integration and continuous delivery (CI/CD) by decoupling deployment
  from release. Code changes can be deployed frequently and then use flags to control when features are made available
  to users.
- Targeted Rollouts: They allow for gradual rollouts to specific user segments. This is useful for beta testing, A/B
  testing, and gathering feedback before a full release.
- Improved Collaboration: Feature flags facilitate collaboration between developers, product managers, and other
  stakeholders. They provide a clear way to manage and control the release of new features.

## Guidelines

1. Feature flags should be created within namespaces that identify the feature. This ensures that feature flags for a
   specific area are easy to find.

2. Feature flags should be short-lived. Once a feature has passed quality assurance and has been enabled as part of a
   release there should be a plan in place to remove the feature flag to make the feature a permanent aspect of the
   application. This includes removing any functionality that is being replaced by the feature.

3. Feature flags should be accessible from a response from `/nbs/api/configuration` endpoint within the `features`
   property.

4. Feature flags should default to the values that are expected to be visible in a production environment.

## Use cases

Any feature flag that affects functionality of the [modernized-ui](/apps/modernization-ui) is defined in
the [configuration-api](/libs/configuration-api) and exposed by the
`/nbs/api/configuration` endpoint.

The implementation of a feature flag depends on what type of feature is being developed.

### New feature on an existing modernized page

A [FeatureToggle](/apps//modernization-ui/src/feature/FeatureToggle.tsx) component has been created to render it's
`children` only when the [guard](/apps//modernization-ui/src/feature/guard.ts) evaluates to `true`. The component also
allows a `fallback` component to be shown when the guard fails.

Example: The Exciting feature is shown when the `features.exciting.enabled` configuration value is true, otherwise the
boring feature is shown

```tsx
<FeatureToggle
    guard={(features) => features.exciting.enabled}
    fallback={<p>Boring feature.</p>}
>
    <div>Exciting feature!</div>
</FeatureToggle>
```

### A completely new page within the modernized application

There are two components for enabling access to an entire page.
The [FeatureGuard](/apps/modernization-ui/src/feature/FeatureGuard.tsx) is a specialized `FeatureToggle` that will
redirect the user the "Home" page if the `guard` fails. This is useful when defining routing to a single resource
that is controlled by a feature flag.

Example: A user is able to navigate to the Exciting page when the `features.exciting.enabled` configuration value is
true.

```tsx
const routing: RouteObject[] = [
    {
        path: "/boring",
        element: <p>Boring feature.</p>,
    },
    {
        path: "/exciting",
        element: (
            <FeatureGuard guard={(features) => features.exciting.enabled}>
                <div>Exciting feature!</div>
            </FeatureGuard>
        ),
    },
];
```

The [FeatureLayout](/apps/modernization-ui/src/feature/FeatureLayout.tsx) can be used when defining routing to multiple
resources that share a similar root path that is controlled by a feature flag. The component declares
an [<Outlet />](https://reactrouter.com/6.29.0/components/outlet) as it's child so that access to `children` paths is
also controlled by the `guard`.

Example: A user is able to navigate to the `/exciting`, `/exciting/super`, and `/exciting/amazing` feature pages when
the `features.exciting.enabled` configuration value is `true`.

```tsx
const routing: RouteObject[] = [
    {
        path: "/boring",
        element: <p>Boring feature.</p>,
    },
    {
        path: "/exciting",
        element: <FeatureLayout guard={(features) => features.exciting.enabled}/>,
        children: [
            {
                path: "super",
                element: <div>Super feature!</div>,
            },
            {
                path: "amazing",
                element: <div>Amazing feature!</div>,
            },
        ],
    },
];
```

### Modernizing an existing page

When creating a modernized version of an existing page, a feature flag should be creating
for [adding a new page](#a-completely-new-page-within-the-modernized-application) in addition to allowing routing to the
modernized page over the existing page. A route locator should be created within
the [nbs-gateway](/apps/nbs-gateway/README.md#strangler-fig-routing) to intercept requests to the existing page with a redirect to the
modernized page.
