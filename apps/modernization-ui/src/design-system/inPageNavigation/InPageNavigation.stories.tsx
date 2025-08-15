import { Meta, StoryObj } from '@storybook/react-vite';
import { InPageNavigation, NavSection, InPageNavigationProps } from './InPageNavigation';

const meta: Meta<InPageNavigationProps> = {
    title: 'Design System/InPageNavigation',
    component: InPageNavigation
};

export default meta;

type Story = StoryObj<typeof meta>;

const sections: NavSection[] = [
    { id: 'apple', label: 'Apple' },
    { id: 'banana', label: 'Banana' },
    { id: 'mango', label: 'Mango' },
    { id: 'orange', label: 'Orange' },
    { id: 'watermelon', label: 'Watermelon' }
];

export const Default: Story = {
    args: {
        title: 'Fruits',
        sections: sections
    }
};
