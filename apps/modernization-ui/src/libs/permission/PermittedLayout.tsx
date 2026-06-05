import { Outlet } from 'react-router';
import { RedirectHome } from 'routes';
import { Permitted, PermittedProps } from './Permitted';

type PermittedLayoutProps = Pick<PermittedProps, 'permission'>;

const PermittedLayout = ({ permission }: PermittedLayoutProps) => (
    <Permitted permission={permission} fallback={<RedirectHome />}>
        <Outlet />
    </Permitted>
);

export { PermittedLayout };
