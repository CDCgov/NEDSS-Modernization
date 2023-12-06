import { ConceptTable } from './ConceptTable';
import { render } from '@testing-library/react';
import { Concept } from './Concept';
import { WithinTableProvider } from 'components/Table/testing';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { findAllByRole } = render(
            <WithinTableProvider>
                <ConceptTable summaries={[]} />
            </WithinTableProvider>
        );

        const tableHeads = await findAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Local Code');
        expect(tableHeads[1]).toHaveTextContent('UI display Name');
        expect(tableHeads[2]).toHaveTextContent('Concept Code');
        expect(tableHeads[3]).toHaveTextContent('Effective Date');
    });
});

describe('when at least one summary is available', () => {
    const questionsSummary: any = {
        localCode: 'ARBO',
        codesetName: 'CONDITION_FAMILY',
        display: 'Arboviral',
        description: 'Arboviral',
        conceptCode: 'ARBO',
        messagingConceptName: 'Arboviral',
        codeSystem: 'Local',
        status: 'A',
        effectiveFromTime: '2015-01-01T00:00:00Z',
        effectiveToTime: null
    };
    const summaries = [questionsSummary];

    it('should display the Concept details in table', async () => {
        const { findAllByRole } = render(
            <WithinTableProvider>
                <ConceptTable summaries={summaries} />
            </WithinTableProvider>
        );

        const tableData = await findAllByRole('cell');
        expect(tableData[0]).toHaveTextContent('ARBO');
        expect(tableData[1]).toHaveTextContent('Arboviral');
        expect(tableData[2]).toHaveTextContent('ARBO');
        expect(tableData[3]).toHaveTextContent('12/31/2014');
    });
});
describe('Concept component tests', () => {
    it('should render a grid with 5 inputs labels which are Local code, UI Display name, Concept Code, Always Effective and Effective Until', () => {
        const { getByText } = render(<Concept />);
        expect(
            getByText('No value set concept is displayed. Please click the button below to add new value set concept.')
        ).toBeInTheDocument();
    });
});
