import { PageProvider } from 'page';
import Logout from './Logout';

const routing = {
    path: '/goodbye',
    element: (
        <PageProvider>
            <Logout />
        </PageProvider>
    )
};

export { routing };
