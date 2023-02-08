import { ComponentStory, ComponentMeta } from '@storybook/react';
import { Input } from '../components/FormInputs/Input';

export default {
    title: 'Components/Input',
    component: Input
} as ComponentMeta<typeof Input>;

const Template: ComponentStory<typeof Input> = (args) => <Input {...args} />;

export const BasicInput = Template.bind({});
BasicInput.args = {
    label: 'Input Label'
};

export const InputWithDefaultValue = Template.bind({});
InputWithDefaultValue.args = {
    label: 'Input Label',
    defaultValue: 'This is default'
};

export const InputWithError = Template.bind({});
InputWithError.args = {
    label: 'Input Label',
    error: 'Please enter a valid input'
};
