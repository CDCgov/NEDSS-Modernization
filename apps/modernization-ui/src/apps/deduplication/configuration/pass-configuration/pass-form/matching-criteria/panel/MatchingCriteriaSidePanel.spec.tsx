import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { FormProvider, useForm } from 'react-hook-form';
import { MatchingCriteriaSidePanel } from './MatchingCriteriaSidePanel';

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
            matchingCriteria: [],
            active: true
        }
    });

    return (
        <FormProvider {...form}>
            <MatchingCriteriaSidePanel visible={visible} onAccept={onAccept} onCancel={onCancel} />
        </FormProvider>
    );
};

// tests have to use waitFor since panel initializes in closed state
describe('MatchingCriteriaSidePanel', () => {
    it('should display first name attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());
    });

    it('should display last name attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Last name')).toBeInTheDocument());
    });

    it('should display suffix attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Suffix')).toBeInTheDocument());
    });

    it('should display date of birth attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Date of birth')).toBeInTheDocument());
    });

    it('should display sex attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Sex')).toBeInTheDocument());
    });

    it('should display race attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Race')).toBeInTheDocument());
    });

    it('should display Address attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Address')).toBeInTheDocument());
    });

    it('should display city attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('City')).toBeInTheDocument());
    });

    it('should display State attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('State')).toBeInTheDocument());
    });

    it('should display Zip attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Zip')).toBeInTheDocument());
    });

    it('should display county attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('County')).toBeInTheDocument());
    });

    it('should display Phone attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Phone')).toBeInTheDocument());
    });

    it('should display email attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Email')).toBeInTheDocument());
    });

    it('should display Identifier attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Identifier')).toBeInTheDocument());
    });

    it('should display ssn attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Social security number')).toBeInTheDocument());
    });

    it('should display drivers license attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText("Driver's license")).toBeInTheDocument());
    });

    it('should display medicaid attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Medicaid number')).toBeInTheDocument());
    });

    it('should display mrn attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Medical record number')).toBeInTheDocument());
    });

    it('should display account number attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Account number')).toBeInTheDocument());
    });

    it('should display national unique attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('National unique individual identifier')).toBeInTheDocument());
    });

    it('should display patient external attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Patient external identifier')).toBeInTheDocument());
    });

    it('should display patient internal attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Patient internal identifier')).toBeInTheDocument());
    });

    it('should display person number attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('Person number')).toBeInTheDocument());
    });

    it('should display visa attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('VISA / Passport number')).toBeInTheDocument());
    });

    it('should display WIC attribute', async () => {
        const { queryByText } = render(<Fixture />);
        await waitFor(() => expect(queryByText('WIC identifier')).toBeInTheDocument());
    });

    it('should not render fields when closed', () => {
        const { queryByText } = render(<Fixture visible={false} />);
        expect(queryByText('First name')).not.toBeInTheDocument();
    });

    it('should update state when checkbox is clicked', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const checkbox = getByLabelText('First name'); // First name
        userEvent.click(checkbox);
        expect(checkbox).toBeChecked();

        userEvent.click(checkbox); // Last name
        expect(checkbox).not.toBeChecked();
    });

    it('should trigger onCancel when cancel is clicked', async () => {
        const { getAllByRole, queryByText } = render(<Fixture />);

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const buttons = getAllByRole('button');
        expect(buttons[1]).toHaveTextContent('Cancel');
        userEvent.click(buttons[1]);

        expect(onCancel).toBeCalledTimes(1);
    });

    it('should trigger onAccept when Add attribute(s) is clicked', async () => {
        const { getAllByRole, queryByText } = render(<Fixture />);

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const buttons = getAllByRole('button');
        expect(buttons[2]).toHaveTextContent('Add attribute(s)');
        userEvent.click(buttons[2]);

        expect(onAccept).toBeCalledTimes(1);
    });
});
