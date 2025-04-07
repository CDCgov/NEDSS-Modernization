import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { FormProvider, useForm } from 'react-hook-form';
import { BlockingCriteriaSidePanel } from './BlockingCriteriaSidePanel';

const onAccept = jest.fn();
const onCancel = jest.fn();

class ResizeObserver {
    observe() {
        // do nothing
    }
    unobserve() {
        // do nothing
    }
    disconnect() {
        // do nothing
    }
}

window.ResizeObserver = ResizeObserver;
export default ResizeObserver;

type Props = {
    visible?: boolean;
};
const Fixture = ({ visible = true }: Props) => {
    const form = useForm<Pass>({
        defaultValues: {
            name: 'Pass name',
            description: 'This is my description for this pass',
            blockingCriteria: [BlockingAttribute.FIRST_NAME],
            active: true
        }
    });
    return (
        <FormProvider {...form}>
            <BlockingCriteriaSidePanel visible={visible} onAccept={onAccept} onCancel={onCancel} />
        </FormProvider>
    );
};

// tests have to use waitFor since panel initializes in closed state
describe('BlockingCriteriaSidePanel', () => {
    it('should display first name attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());
        await waitFor(() =>
            expect(queryByText("The first 4 characters of the person's first name.")).toBeInTheDocument()
        );
    });

    it('should display last name attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Last name')).toBeInTheDocument());
        await waitFor(() =>
            expect(queryByText("The first 4 characters of the person's last name.")).toBeInTheDocument()
        );
    });

    it('should display date of birth attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Date of birth')).toBeInTheDocument());
        await waitFor(() =>
            expect(queryByText("The person's birthdate in the format YYYY-MM-DD.")).toBeInTheDocument()
        );
    });

    it('should display sex attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Sex')).toBeInTheDocument());
        await waitFor(() => expect(queryByText("The person's sex in the format of M or F.")).toBeInTheDocument());
    });

    it('should display street address attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Street address 1')).toBeInTheDocument());
        await waitFor(() => expect(queryByText("The first 4 characters of the person's address.")).toBeInTheDocument());
    });

    it('should display zip attribute', async () => {
        const { queryByText } = render(<Fixture />);

        await waitFor(() => expect(queryByText('Zip')).toBeInTheDocument());
        await waitFor(() => expect(queryByText("The person's 5 digit zip code.")).toBeInTheDocument());
    });

    it('should display phone attribute', async () => {
        const { queryByText } = render(<Fixture />);

        await waitFor(() => expect(queryByText('Phone')).toBeInTheDocument());
        await waitFor(() => expect(queryByText("The last 4 digits of the person's phone number.")).toBeInTheDocument());
    });

    it('should not render fields when closed', () => {
        const { queryByText } = render(<Fixture visible={false} />);
        expect(queryByText('First name')).not.toBeInTheDocument();
    });

    it('should update selected when checkbox is clicked ', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const user = userEvent.setup();

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const checkbox = getByLabelText('First name'); // First name
        expect(checkbox).toBeChecked();
        await user.click(checkbox);

        expect(checkbox).not.toBeChecked();
        await user.click(checkbox);
        expect(checkbox).toBeChecked();
    });

    it('should trigger onCancel when cancel is clicked', async () => {
        const { getByRole, queryByText } = render(<Fixture />);

        const user = userEvent.setup();

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const cancel = getByRole('button', { name: 'Cancel' });
        expect(cancel).toHaveTextContent('Cancel');
        await user.click(cancel);

        expect(onCancel).toBeCalledTimes(1);
    });

    it('should trigger onAccept when Add attribute(s) is clicked', async () => {
        const { getByRole, queryByText } = render(<Fixture />);

        const user = userEvent.setup();

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const add = getByRole('button', { name: 'Add attribute(s)' });
        expect(add).toHaveTextContent('Add attribute(s)');

        await user.click(add);

        expect(onAccept).toBeCalledTimes(1);
    });
});
