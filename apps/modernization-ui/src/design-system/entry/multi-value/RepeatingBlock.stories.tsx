import { Meta, StoryObj } from '@storybook/react-vite';
import { RepeatingBlock } from './RepeatingBlock';
import { Controller, useFormContext } from 'react-hook-form';
import { SingleSelect } from 'design-system/select';
import { asSelectable, Selectable } from 'options';
import { Input } from 'components/FormInputs/Input';
import { Sizing } from 'design-system/field';
import { ValueView } from 'design-system/data-display/ValueView';

type SampleType = {
    firstName: string;
    lastName: string;
    veggie: Selectable | undefined;
};

const meta = {
    title: 'Design System/Multi-value/RepeatingBlock',
    component: RepeatingBlock<SampleType>
} satisfies Meta<typeof RepeatingBlock<SampleType>>;

export default meta;

type Story = StoryObj<typeof meta>;

const options: Selectable[] = [
    asSelectable('carrot', 'Carrot'),
    asSelectable('eggplant', 'Eggplant'),
    asSelectable('onion', 'Onion'),
    asSelectable('potato', 'Potato'),
    asSelectable('tomato', 'Tomato')
];

const SampleForm = ({ sizing }: { sizing: Sizing }) => {
    const { control } = useFormContext<SampleType>();
    return (
        <section>
            <Controller
                name="firstName"
                control={control}
                rules={{ required: { value: true, message: 'First name is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        flexBox
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="First name"
                        id={name}
                        sizing={sizing}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                name="lastName"
                control={control}
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <Input
                        flexBox
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Last name"
                        sizing={sizing}
                        id={name}
                    />
                )}
            />
            <Controller
                name="veggie"
                control={control}
                rules={{ required: { value: true, message: 'Favorite veggie is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Favorite veggie"
                        orientation="horizontal"
                        value={value}
                        onBlur={onBlur}
                        sizing={sizing}
                        onChange={onChange}
                        id={name}
                        options={options}
                        error={error?.message}
                        placeholder="Select a veggie"
                        required
                    />
                )}
            />
        </section>
    );
};

const SampleView = ({ entry, sizing }: { entry: SampleType; sizing: Sizing }) => (
    <>
        <ValueView title="First name" value={entry.firstName} sizing={sizing} />
        <ValueView title="Last name" value={entry.lastName} sizing={sizing} />
        <ValueView title="Favorite veggie" value={entry.veggie?.name} sizing={sizing} />
    </>
);

const columns = [
    {
        id: 'firstName',
        name: 'First name',
        value: (entry: SampleType) => entry.firstName
    },
    {
        id: 'lastName',
        name: 'Last name',
        value: (entry: SampleType) => entry.lastName
    },
    {
        id: 'veggie',
        name: 'Favorite veggie',
        value: (entry: SampleType) => entry.veggie?.name
    }
];

const defaultValue: Partial<SampleType> = {
    firstName: undefined,
    lastName: undefined,
    veggie: undefined
};

const defaultSizing: Sizing = 'small';

const handleChange = (values: SampleType[]) => {
    console.log('Values:', values);
};

export const Default: Story = {
    args: {
        id: 'repeating-block-default',
        title: 'Person',
        defaultValues: defaultValue,
        columns,
        data: [
            { firstName: 'test', lastName: 'test', veggie: asSelectable('carrot', 'Carrot') },
            { firstName: 'test1', lastName: 'test1', veggie: asSelectable('eggplant', 'Eggplant') }
        ],
        formRenderer: () => <SampleForm sizing={defaultSizing} />,
        viewRenderer: (entry: SampleType) => <SampleView entry={entry} sizing={defaultSizing} />,
        onChange: handleChange,
        isDirty: () => {},
        isValid: () => {}
    }
};

export const ViewOnly: Story = {
    args: {
        ...Default.args,
        viewable: true,
        editable: false
    }
};

export const EditableOnly: Story = {
    args: {
        ...Default.args,
        viewable: false,
        editable: true
    }
};
