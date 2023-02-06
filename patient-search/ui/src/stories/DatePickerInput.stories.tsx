import { ComponentStory, ComponentMeta } from '@storybook/react';
import { DatePickerInput } from "../components/FormInputs/DatePickerInput";

export default {
    title: 'Components/DatePickerInput',
    component: DatePickerInput
} as ComponentMeta<typeof DatePickerInput>;

const Template: ComponentStory<typeof DatePickerInput> = (args) => <DatePickerInput {...args} />;

export const DatePicker = Template.bind({});
DatePicker.args = {
    label: 'Select Date'
};

export const DatePickerWithDefaultValue = Template.bind({});
DatePickerWithDefaultValue.args = {
    label: 'Select Date',
    defaultValue: '06/01/2019'
};
