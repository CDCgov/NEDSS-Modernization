import { Meta, StoryObj } from '@storybook/react';
import { TextAutocomplete } from './TextAutocomplete';
import { asSelectable, Selectable } from 'options';

const meta = {
    title: 'Design System/Autocomplete/TextAutocomplete',
    component: TextAutocomplete
} satisfies Meta<typeof TextAutocomplete>;

export default meta;

type Story = StoryObj<typeof meta>;

const options: Selectable[] = [
    asSelectable('apple', 'Apple'),
    asSelectable('banana', 'Banana'),
    asSelectable('mango', 'Mango'),
    asSelectable('orange', 'Orange'),
    asSelectable('watermelon', 'Watermelon')
];

const resolver = (searchText: string) => {
    return Promise.resolve(options.filter((option) => option.name.toLowerCase().includes(searchText.toLowerCase())));
};

export const Default: Story = {
    args: {
        id: 'selectautocomplete-default',
        name: 'selectautocomplete-default',
        label: 'Default',
        resolver: resolver,
        placeholder: 'Type text'
    }
};
