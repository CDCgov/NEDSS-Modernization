import { ReactNode } from 'react';
import { FeatureToggle, FeatureToggleProps } from 'feature';
import { Permitted, PermittedProps } from 'libs/permission';

type GuardedProps = {
    feature: FeatureToggleProps['guard'];
    permission: PermittedProps['permission'];
    fallback?: ReactNode;
    children: ReactNode;
};

/**
 * A composition of {@link Permitted} and {@link FeatureToggle} where features are resolved first, then permissions.
 *
 * @param {GuardedProps} props
 * @return {ReactNode} The component displayed when the feature and permission evaluation passes.
 */
const Guarded = ({ permission, feature, fallback, children }: GuardedProps) => (
    <FeatureToggle guard={feature} fallback={fallback}>
        <Permitted permission={permission} fallback={fallback}>
            {children}
        </Permitted>
    </FeatureToggle>
);

export { Guarded };
export type { GuardedProps };
