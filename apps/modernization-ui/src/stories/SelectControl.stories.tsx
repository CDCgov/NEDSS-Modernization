import { ComponentStory, ComponentMeta } from '@storybook/react';
import { useForm } from 'react-hook-form';
import { SelectControl } from '../components/FormInputs/SelectControl';

export default {
    title: 'Components/SelectControl',
    component: SelectControl
} as ComponentMeta<typeof SelectControl>;

const Template: ComponentStory<typeof SelectControl> = (args) => {
    const methods = useForm();
    args.control = methods.control;
    return <SelectControl {...args} />;
};

export const SampleSelect = Template.bind({});
SampleSelect.args = {
    name: 'case',
    label: 'Multi Select',
    options: [
        { name: 'option 1', value: 'option 1' },
        { name: 'option 2', value: 'option 2' },
        { name: 'option 3', value: 'option 3' }
    ],
    isMulti: false
};

export const MultiSelect = Template.bind({});
MultiSelect.args = {
    name: 'case',
    label: 'Multi Select',
    options: [
        { name: 'option 1', value: 'option 1' },
        { name: 'option 2', value: 'option 2' },
        { name: 'option 3', value: 'option 3' }
    ],
    isMulti: true
};
