import { ComponentStory, ComponentMeta } from '@storybook/react';
import { useForm } from 'react-hook-form';
import { MultiSelectControl } from '../components/FormInputs/MultiSelectControl';

export default {
    title: 'Components/MultiSelectControl',
    component: MultiSelectControl
} as ComponentMeta<typeof MultiSelectControl>;

const Template: ComponentStory<typeof MultiSelectControl> = (args) => {
    const methods = useForm();
    args.control = methods.control;
    return <MultiSelectControl {...args} />
};

export const BasicMultiSelect = Template.bind({});

BasicMultiSelect.args = {
    name: 'case',
    label: 'Multi Select',
    options: [
        { name: 'option 1', value: 'option 1'},
        { name: 'option 2', value: 'option 2'},
        { name: 'option 3', value: 'option 3'},
    ]
};
