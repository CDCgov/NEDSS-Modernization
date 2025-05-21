import { PageProvider, PageTitle } from 'page';

const routing = [
    {
        path: '/system_management',
        element: (
            <PageProvider>
                <PageTitle title="System Management">
                    <></>
                </PageTitle>
            </PageProvider>
        )
    }
];

export { routing };
