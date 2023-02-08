import { ComponentStory, ComponentMeta } from '@storybook/react';
import { SelectInput } from '../components/FormInputs/SelectInput';

export default {
    title: 'Components/SelectInput',
    component: SelectInput,
    argTypes: {
        color: { control: { type: 'select', options: ['primary', 'secondary'] } }
    }
} as ComponentMeta<typeof SelectInput>;

const Template: ComponentStory<typeof SelectInput> = (args) => <SelectInput {...args} />;

export const Select = Template.bind({});
Select.args = {
    label: 'Select Dropdown',
    options: [
        { name: 'option 1', value: 'option 1' },
        { name: 'option 2', value: 'option 2' },
        { name: 'option 3', value: 'option 3' }
    ]
};
