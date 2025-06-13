import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { FormProvider, useForm } from 'react-hook-form';
import { MemoryRouter, Route, Routes } from 'react-router';
import { MergeReview } from './MergeReview';
import { PatientMergeForm } from './model/PatientMergeForm';
import { AlertProvider } from 'alert';

const onPreview = vi.fn();
const onRemove = vi.fn();

const mockKeepAllSeparate = vi.fn();
vi.mock('apps/deduplication/api/useRemoveMerge', () => ({
    useRemoveMerge: () => {
        return { keepAllSeparate: mockKeepAllSeparate };
    }
}));

const Fixture = () => {
    const form = useForm<PatientMergeForm>();
    const data: Partial<MergeCandidate>[] = [
        {
            personUid: '100',
            personLocalId: '001',
            adminComments: { date: '2025-05-01T00:00', comment: 'First comment' },
            ethnicity: {},
            sexAndBirth: {},
            mortality: {},
            general: {},
            investigations: []
        },
        {
            personUid: '200',
            personLocalId: '002',
            adminComments: { date: '2005-01-21T00:00', comment: 'Second comment' },
            ethnicity: {},
            sexAndBirth: {},
            mortality: {},
            general: {},
            investigations: []
        },
        {
            personUid: '300',
            personLocalId: '003',
            adminComments: { date: '1995-04-23T00:00', comment: 'Third comment' },
            ethnicity: {},
            sexAndBirth: {},
            mortality: {},
            general: {},
            investigations: []
        }
    ];
    return (
        <AlertProvider>
            <MemoryRouter initialEntries={['/deduplication/merge/1234']}>
                <Routes>
                    <Route
                        path="/deduplication/merge/:matchId"
                        element={
                            <FormProvider {...form}>
                                <MergeReview
                                    mergeCandidates={data as MergeCandidate[]}
                                    onPreview={onPreview}
                                    onRemovePatient={onRemove}
                                />
                            </FormProvider>
                        }
                    />
                </Routes>
            </MemoryRouter>
        </AlertProvider>
    );
};

describe('MergeReview', () => {
    it('should display proper header', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Patient matches requiring review');
    });

    it('should display buttons in header', () => {
        const { getAllByRole } = render(<Fixture />);
        const buttons = getAllByRole('button');

        expect(buttons[0]).toHaveTextContent('Back');
        expect(buttons[0]).toHaveClass('secondary');
        expect(buttons[1]).toHaveTextContent('Preview merge');
        expect(buttons[1]).toHaveClass('secondary');
        expect(buttons[2]).toHaveTextContent('Keep all separate');
        expect(buttons[2]).not.toHaveClass('secondary');
        expect(buttons[3]).toHaveTextContent('Merge all');
        expect(buttons[3]).not.toHaveClass('secondary');
    });

    it('should handle preview click', async () => {
        const user = userEvent.setup();
        const { getByText } = render(<Fixture />);

        await user.click(getByText('Preview merge'));
        expect(onPreview).toHaveBeenCalled();
    });

    it('should display informational text', () => {
        const { getByText } = render(<Fixture />);
        expect(
            getByText(
                'Only one record is selected for Patient ID. By default, the oldest record is selected as the surviving ID. If this is not correct, select the appropriate record.'
            )
        ).toBeInTheDocument();
    });

    it('should display patient id selection', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('001')).toBeInTheDocument();
        expect(getByLabelText('002')).toBeInTheDocument();
        expect(getByLabelText('003')).toBeInTheDocument();
    });

    it('should handle patient remove', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        await user.click(getAllByRole('button', { name: 'Remove' })[0]);
        expect(onRemove).toHaveBeenLastCalledWith('100');
    });

    it('should call keep all separate when button clicked', async () => {
        const user = userEvent.setup();
        const { getByText } = render(<Fixture />);

        const keepSeparateButton = getByText('Keep all separate');
        await user.click(keepSeparateButton);

        expect(mockKeepAllSeparate).toHaveBeenCalledWith('1234', expect.any(Function), expect.any(Function));
    });
});
