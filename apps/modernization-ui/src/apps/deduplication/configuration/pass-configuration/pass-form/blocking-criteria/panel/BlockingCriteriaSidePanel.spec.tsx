import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { BlockingCriteriaSidePanel } from './BlockingCriteriaSidePanel';
import { useState } from 'react';

const onAccept = jest.fn();
const onCancel = jest.fn();
const onChange = jest.fn();

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
    initial?: BlockingAttribute[];
};
const Fixture = ({ initial }: Props) => {
    const [selectedAttributes, setSelectedAttributes] = useState<BlockingAttribute[]>(initial ?? []);
    return (
        <BlockingCriteriaSidePanel
            selectedAttributes={selectedAttributes}
            visible={true}
            onAccept={onAccept}
            onCancel={onCancel}
            onChange={(a) => {
                onChange(a);
                setSelectedAttributes(a);
            }}
        />
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
        const { queryByText } = render(
            <BlockingCriteriaSidePanel
                selectedAttributes={[]}
                visible={false}
                onAccept={onAccept}
                onCancel={onCancel}
                onChange={onChange}
            />
        );
        expect(queryByText('First name')).not.toBeInTheDocument();
    });

    it('should trigger on change when checkbox is clicked', async () => {
        const { getAllByRole, queryByText } = render(<Fixture />);

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const checkboxes = getAllByRole('checkbox');
        userEvent.click(checkboxes[0]); // First name
        expect(onChange).toBeCalledWith([BlockingAttribute.FIRST_NAME]);

        userEvent.click(checkboxes[1]); // Last name
        expect(onChange).toBeCalledWith([BlockingAttribute.FIRST_NAME, BlockingAttribute.LAST_NAME]);
    });

    it('should trigger on change when checkbox is clicked and attributes are already selected', async () => {
        const { getAllByRole, queryByText } = render(<Fixture initial={[BlockingAttribute.FIRST_NAME]} />);

        await waitFor(() => expect(queryByText('First name')).toBeInTheDocument());

        const checkboxes = getAllByRole('checkbox');
        expect(checkboxes[0]).toBeChecked();
        userEvent.click(checkboxes[0]); // First name

        expect(onChange).toBeCalledWith([]);
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
