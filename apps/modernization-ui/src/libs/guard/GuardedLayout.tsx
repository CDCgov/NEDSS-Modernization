import { Outlet } from 'react-router';
import { Guarded, GuardedProps } from './Guarded';

type GuardedLayoutProps = Omit<GuardedProps, 'children'>;

/**
 * A specialized {@link Guarded} with {@link Outlet} as a child used to restrict a path using permissions and features.
 *
 * @param {GuardedLayoutProps} props
 * @return {ReactNode} The component displayed when the feature and permission evaluation passes.
 */
const GuardedLayout = (props: GuardedLayoutProps) => (
    <Guarded {...props}>
        <Outlet />
    </Guarded>
);

export { GuardedLayout };
