import { ConceptTable } from './ConceptTable';
import { render } from '@testing-library/react';
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
