import { render } from '@testing-library/react';
import { ImportModal } from './ImportModal';
import userEvent from '@testing-library/user-event';

const onCancel = jest.fn();
const onImport = jest.fn();

type Props = {
    visible?: boolean;
    title?: string;
    infoText?: string;
    dropSectionContent?: string;
    error?: string;
    accept?: string;
};
const Fixture = ({ visible = true, title, infoText, dropSectionContent, error, accept }: Props) => {
    return (
        <ImportModal
            id="testModal"
            visible={visible}
            title={title}
            infoText={infoText}
            dropSectionContent={dropSectionContent}
            error={error}
            accept={accept}
            onCancel={onCancel}
            onImport={onImport}
        />
    );
};

describe('ImportModal', () => {
    // Heading
    it('should display default heading', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Import file')).toBeInTheDocument();
    });
    it('should allow override of heading', () => {
        const { getByText } = render(<Fixture title={'Custom title'} />);

        expect(getByText('Custom title')).toBeInTheDocument();
    });

    // Label content
    it('should display default label content', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Drag file here or choose from folder')).toBeInTheDocument();
    });

    it('should display allow override of label content', () => {
        const { getByLabelText } = render(<Fixture dropSectionContent={'Updated content'} />);

        expect(getByLabelText('Updated content')).toBeInTheDocument();
    });

    // Info text
    it('should show specified info text', () => {
        const { getByText } = render(<Fixture infoText="Some additonal info" />);

        expect(getByText('Some additonal info')).toBeInTheDocument();
    });

    // Visible
    it('should be hidden when not visible', () => {
        const { queryByText } = render(<Fixture visible={false} infoText="Some additonal info" />);

        expect(queryByText('Import file')).not.toBeInTheDocument();
        expect(queryByText('Some additonal info')).not.toBeInTheDocument();
    });

    it('should not be hidden when visible', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Import file')).toBeInTheDocument();
    });

    // Error
    it('should display error text', () => {
        const { getByText } = render(<Fixture error="Something went wrong" />);

        expect(getByText('Something went wrong')).toBeInTheDocument();
    });

    // Accept
    it('should allow limiting of input type', () => {
        const { getByLabelText } = render(<Fixture accept=".json,.csv" />);

        expect(getByLabelText('Drag file here or choose from folder')).toHaveAttribute('accept', '.json,.csv');
    });

    // Cancel - close button
    it('should trigger on cancel when close is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const closeButton = getAllByRole('button')[0]; // X at top right
        await user.click(closeButton);

        expect(onCancel).toHaveBeenCalledTimes(1);
    });

    // Cancel - cancel button
    it('should trigger on cancel when cancel is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const closeButton = getAllByRole('button')[1];
        expect(closeButton).toHaveTextContent('Cancel');
        await user.click(closeButton);

        expect(onCancel).toHaveBeenCalledTimes(1);
    });

    // File select
    it('should trigger on onImport when file is imported', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getAllByRole } = render(<Fixture />);

        const file = new File(['test content'], 'test.txt', { type: 'text/plain' });
        const input = getByLabelText('Drag file here or choose from folder');
        await user.upload(input, file);

        const importButton = getAllByRole('button')[2];
        expect(importButton).toHaveTextContent('Import');
        await user.click(importButton);

        expect(onImport).toHaveBeenCalledTimes(1);
        expect(onImport).toHaveBeenCalledWith(file);
    });
});
