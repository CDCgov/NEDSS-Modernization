import { Meta, StoryObj } from '@storybook/react';
import { SectionHeader } from './SectionHeader';

const meta: Meta<typeof SectionHeader> = {
    title: 'Components/SectionHeader',
    component: SectionHeader,
    args: {
        title: 'Section Title',
        defaultOpen: true,
        children: <div style={{ padding: '1rem 0' }}>Collapsible content goes here.</div>
    }
};

export default meta;

type Story = StoryObj<typeof SectionHeader>;

export const Default: Story = {};

export const WithCounter: Story = {
    args: {
        showCounter: true,
        count: 5
    }
};

export const WithSubtextAndTooltip: Story = {
    args: {
        subtext: 'Some helpful subtext here',
        tooltip: 'Extra context via tooltip'
    }
};

export const Tall: Story = {
    args: {
        tall: true
    }
};

export const Slim: Story = {
    args: {
        slim: true
    }
};

export const AllTogether: Story = {
    args: {
        showCounter: true,
        count: 3,
        subtext: 'Subtext with tooltip',
        tooltip: 'Helpful context',
        tall: true
    }
};
