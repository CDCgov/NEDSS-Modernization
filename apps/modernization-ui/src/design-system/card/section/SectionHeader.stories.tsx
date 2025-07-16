import React from 'react';
import { Meta, StoryObj } from '@storybook/react';
import { SectionHeader } from './SectionHeader';
import { Button } from 'design-system/button';
import { Column, DataTable } from '../../table';

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
        title: 'Basic Header'
    }
};

export const WithSubtext: Story = {
    args: {
        title: 'Title Header',
        subtext: 'subtext'
    }
};

export const WithSubtextAndTooltip: Story = {
    args: {
        title: 'Title Header',
        subtext: 'subtext',
        tooltip: <div>Additional context or explanation about the section goes here.</div>
    }
};

export const WithSubtextAndTooltipAndCounter: Story = {
    args: {
        title: 'Title Header',
        subtext: 'subtext',
        showCounter: true,
        count: 999,
        tooltip: <div>Additional context or explanation about the section goes here.</div>
    }
};

export const WithCounterAndContent: Story = {
    args: {
        title: 'Title Header',
        subtext: 'subtext',
        showCounter: true,
        count: 999,
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
