import { PageProvider } from 'page';
import { ConceptTable } from './ConceptTable';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <AlertProvider>
                        <ConceptTable summaries={[]} />
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Type');
        expect(tableHeads[1].innerHTML).toBe('Unique ID');
        expect(tableHeads[2].innerHTML).toBe('Unique name');
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

    it('should display the valueset', async () => {
        const { container } = render(
            <PageProvider>
                <AlertProvider>
                    <ConceptTable summaries={summaries} />
                </AlertProvider>
            </PageProvider>
        );

        const tableData = container.getElementsByClassName('table-data');
        expect(tableData[0]).toHaveTextContent('ARBO');
        expect(tableData[1]).toHaveTextContent('INV118');
        expect(tableData[2]).toHaveTextContent('Reporting Source Zip');
        expect(tableData[3]).toHaveTextContent('INV');
    });
});
