import { Meta, StoryObj } from '@storybook/react';
import { SearchBar } from './SearchBar';
import { useState } from 'react';

const meta = {
    title: 'Design System/Search/SearchBar',
    component: SearchBar
} satisfies Meta<typeof SearchBar>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {}
};

export const WithPlaceholder: Story = {
    args: {
        tall: false,
        placeholder: 'Medium placeholder search...'
    }
};

export const WithAlternateIcon: Story = {
    args: {
        size: 'medium',
        tall: false,
        placeholder: 'Medium filter search...',
        altIconName: 'filter_alt'
    }
};

export const Small: Story = {
    args: {
        size: 'small',
        tall: false,
        placeholder: 'Search small...'
    }
};

export const Large: Story = {
    args: {
        size: 'large',
        tall: false,
        placeholder: 'Search large...'
    }
};

export const TallSmall: Story = {
    args: {
        size: 'small',
        tall: true,
        placeholder: 'Tall small search...'
    }
};

export const TallMedium: Story = {
    args: {
        size: 'medium',
        tall: true,
        placeholder: 'Tall medium search...'
    }
};

export const Controlled: Story = {
    render: (args) => {
        const [value, setValue] = useState('');

        return <SearchBar {...args} value={value} onChange={(newVal) => setValue(newVal)} />;
    },
    args: {
        size: 'medium',
        tall: false,
        placeholder: 'Type and clear me!'
    }
};
