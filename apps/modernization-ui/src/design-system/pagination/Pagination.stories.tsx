import type { Meta, StoryObj } from '@storybook/react-vite';
import { Pagination } from './Pagination';

const meta = {
    title: 'Design System/Pagination',
    component: Pagination
} satisfies Meta<typeof Pagination>;

export default meta;

type Story = StoryObj<typeof meta>;

const handlePageChange = (page: number) => {
    console.log('page', page);
};

const handleNextPage = () => {
    console.log(' next page');
};

const handlePrevPage = () => {
    console.log('prev page');
};

export const Default: Story = {
    args: {
        total: 100,
        current: 1,
        onSelectPage: handlePageChange,
        onNext: handleNextPage,
        onPrevious: handlePrevPage
    }
};

export const NoResults: Story = {
    ...Default,
    args: {
        total: 0,
        current: 1,
        onSelectPage: handlePageChange,
        onNext: handleNextPage,
        onPrevious: handlePrevPage
    }
};
