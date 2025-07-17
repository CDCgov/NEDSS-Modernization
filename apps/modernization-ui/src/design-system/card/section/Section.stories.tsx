import React from 'react';
import { Meta, StoryObj } from '@storybook/react';
import { SectionHeader } from './Section';
import { Button } from 'design-system/button';
import { Column, DataTable } from '../../table';
import { Hint } from 'design-system/hint';
import { Tag } from 'design-system/tag';

type Person = {
    id: number;
    name: string;
    age: number;
};

const columns: Column<Person>[] = [
    {
        id: 'name',
        name: 'Name',
        value: (person) => person.name,
        sortable: true,
        sortIconType: 'alpha'
    },
    {
        id: 'age',
        name: 'Age',
        value: (person) => person.age,
        sortable: true,
        sortIconType: 'numeric'
    }
];

const data: Person[] = [
    { id: 1, name: 'Jane Doe', age: 32 },
    { id: 2, name: 'John Smith', age: 28 },
    { id: 3, name: 'Alice Johnson', age: 45 }
];

const meta: Meta<typeof SectionHeader> = {
    title: 'Design System/SectionHeader',
    component: SectionHeader
};

export default meta;

type Story = StoryObj<typeof SectionHeader>;

export const Basic: Story = {
    args: {
        id: 'basic-header',
        title: 'Basic Header'
    }
};

export const WithSubtext: Story = {
    args: {
        id: 'with-subtext',
        title: 'Title Header',
        subtext: 'subtext'
    }
};

export const WithSubtextAndTooltip: Story = {
    args: {
        id: 'with-subtext-and-tooltip',
        title: 'Title Header',
        subtext: (
            <div style={{ display: 'flex', alignItems: 'center' }}>
                subtext <Hint id="tooltip-text">Additional context or explanation about the section goes here.</Hint>
            </div>
        )
    }
};

export const WithSubtextAndTooltipAndCounter: Story = {
    args: {
        id: 'with-subtext-and-tooltip-and-tag',
        title: 'Title Header',
        subtext: (
            <div style={{ display: 'flex', alignItems: 'center' }}>
                subtext <Hint id="tooltip-text">Additional context or explanation about the section goes here.</Hint>
            </div>
        ),
        flair: <Tag>999</Tag>
    }
};

export const WithCounterAndContent: Story = {
    args: {
        id: 'with-counter-and-content',
        title: 'Title Header',
        subtext: 'subtext',
        flair: <Tag>999</Tag>,
        children: (
            <div style={{ padding: '1rem' }}>
                <p>This is some content inside the collapsible section.</p>
                <Button secondary>Action</Button>
            </div>
        )
    }
};

export const WithChildrenTableAndCount: StoryObj<typeof SectionHeader> = {
    args: {
        title: 'People Table',
        subtext: data.length + ' persons',
        children: <DataTable id="people-table" columns={columns} data={data} />
    }
};
