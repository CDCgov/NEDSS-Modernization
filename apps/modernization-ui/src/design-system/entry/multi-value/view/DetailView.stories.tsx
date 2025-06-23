import { Meta, StoryObj } from '@storybook/react';
import { DetailValue, DetailView, DetailViewProps } from './DetailView';

const meta = {
    title: 'Design System/Multi-value/DetailView',
    component: DetailView
} satisfies Meta<typeof DetailView>;

export default meta;

type Story = StoryObj<typeof meta>;

const render = (args: DetailViewProps) => (
    <DetailView {...args}>
        <DetailValue label="First name">Hari</DetailValue>
        <DetailValue label="Last name">Puttar</DetailValue>
        <DetailValue label="Age">11</DetailValue>
        <DetailValue label="Email" />
    </DetailView>
);

export const Default: Story = {
    render
};
