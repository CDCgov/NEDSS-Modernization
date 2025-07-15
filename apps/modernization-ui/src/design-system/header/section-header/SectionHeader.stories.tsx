import { Meta, StoryObj } from '@storybook/react';
import { useState } from 'react';
import { SectionHeader } from './SectionHeader';
import { Collapsible } from '../../card/Collapsible';

const meta: Meta<typeof SectionHeader> = {
    title: 'Components/SectionHeader',
    component: SectionHeader
};

export default meta;

type Story = StoryObj<typeof SectionHeader>;

const WithCollapsibleWrapper = (args: any) => {
    const [collapsed, setCollapsed] = useState(false);

    return (
        <>
            <SectionHeader {...args} onToggle={setCollapsed} />
            <Collapsible open={!collapsed}>
                <div style={{ padding: '1rem' }}>Collapsible content goes here.</div>
            </Collapsible>
        </>
    );
};

export const Default: Story = {
    render: () => <WithCollapsibleWrapper title="Section Title" defaultOpen tall />
};

export const WithCounter: Story = {
    render: () => (
        <WithCollapsibleWrapper title="Lab Results Large Large Big Title" showCounter count={5} defaultOpen tall />
    )
};

export const SlimWithCounter: Story = {
    render: () => <WithCollapsibleWrapper title="Lab Results" showCounter count={5} defaultOpen slim />
};

export const WithSubtext: Story = {
    render: () => (
        <WithCollapsibleWrapper
            title="The following superseded patient records were merged with John Smith"
            subtext="2 records"
            tall
        />
    )
};

export const WithTooltipContent: Story = {
    render: () => (
        <WithCollapsibleWrapper
            title="Diagnosis with Hint"
            subtext="2 records"
            tooltipContent={
                <div style={{ maxWidth: '260px' }}>
                    <strong>ICD-10 Codes</strong>
                    <p>This includes diagnoses recorded in the last 6 months, coded using ICD-10.</p>
                </div>
            }
            tall
        />
    )
};

export const WithSubtextAndTooltip: Story = {
    render: () => (
        <WithCollapsibleWrapper
            title="Diagnosis Large Large Big Title"
            subtext="2 records"
            tooltip="ICD-10 coded entries in the last 6 months"
            tall
        />
    )
};

export const Tall: Story = {
    render: () => <WithCollapsibleWrapper title="Vitals" tall />
};

export const Slim: Story = {
    render: () => <WithCollapsibleWrapper title="Medications" slim />
};

export const AllTogether: Story = {
    render: () => (
        <WithCollapsibleWrapper
            title="Conditions"
            showCounter
            count={3}
            subtext="3 records"
            tooltip="Helpful context"
        />
    )
};
