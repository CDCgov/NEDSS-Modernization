import { ComponentStory, ComponentMeta } from '@storybook/react';
import { CheckBoxControl } from '../components/FormInputs/CheckBoxControl';
import { useForm } from 'react-hook-form';

export default {
    title: 'Components/CheckBoxControl',
    component: CheckBoxControl
} as ComponentMeta<typeof CheckBoxControl>;

const Template: ComponentStory<typeof CheckBoxControl> = (args) => {
    const methods = useForm();
    args.control = methods.control;
    return <CheckBoxControl {...args} />
};

export const CheckBox = Template.bind({});
CheckBox.args = {
    id: 'case',
    name: 'case',
    label: 'CheckBox Label'
};
