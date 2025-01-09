import type { Meta, StoryObj } from '@storybook/react';

import { PaginationLayout } from './Pagination';
import { PageProvider } from 'page';
import { MemoryRouter } from 'react-router-dom';

const meta = {
    title: 'Design System/Pagination',
    component: PaginationLayout
} satisfies Meta<typeof PaginationLayout>;

export default meta;

type Story = StoryObj<typeof meta>;

const handlePageChange = (page: number) => {
    console.log('page', page);
};

export const Default: Story = {
    decorators: [
        (Story) => (
            <MemoryRouter>
                <PageProvider>
                    <Story />
                </PageProvider>
            </MemoryRouter>
        )
    ],
    args: {
        request: handlePageChange,
        total: 100,
        pageSize: 10,
        current: 1
    }
};

export const NoResults: Story = {
    ...Default,
    args: {
        total: 0,
        pageSize: 10,
        current: 1
    }
};
