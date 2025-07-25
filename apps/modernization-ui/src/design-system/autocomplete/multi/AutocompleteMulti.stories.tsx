import { Meta, StoryObj } from '@storybook/react-vite';
import { AutocompleteMulti } from './AutocompleteMulti';
import { asSelectable, Selectable } from 'options';

const meta = {
    title: 'Design System/Autocomplete/AutocompleteMulti',
    component: AutocompleteMulti
} satisfies Meta<typeof AutocompleteMulti>;

export default meta;

type Story = StoryObj<typeof meta>;

const options: Selectable[] = [
    asSelectable('apple', 'Apple'),
    asSelectable('banana', 'Banana'),
    asSelectable('mango', 'Mango'),
    asSelectable('orange', 'Orange'),
    asSelectable('watermelon', 'Watermelon')
];
const [, banana, mango] = options;

const resolver = (searchText: string) => {
    return Promise.resolve(options.filter((option) => option.name.toLowerCase().includes(searchText.toLowerCase())));
};

export const Default: Story = {
    args: {
        id: 'autocomplete-default',
        name: 'autocomplete-default',
        label: 'Default',
        options: [...options],
        resolver: resolver,
        placeholder: 'Select options'
    }
};

export const PreselectedOptions: Story = {
    args: {
        id: 'autocomplete-preselected',
        name: 'autocomplete-preselected',
        label: 'Default',
        options: [...options],
        value: [banana, mango],
        resolver: resolver,
        placeholder: 'Select options'
    }
};
