import { PageProvider } from 'page';
import { ConceptTable } from './ConceptTable';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';
import { Concept } from './Concept';

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

        expect(tableHeads[0].innerHTML).toBe('Local Code');
        expect(tableHeads[1].innerHTML).toBe('UI display Name');
        expect(tableHeads[2].innerHTML).toBe('Concept Code');
        expect(tableHeads[3].innerHTML).toBe('Effective Date');
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
        const { container } = render(
            <PageProvider>
                <AlertProvider>
                    <ConceptTable summaries={summaries} />
                </AlertProvider>
            </PageProvider>
        );

        const tableData = container.getElementsByClassName('table-data');
        expect(tableData[0]).toHaveTextContent('ARBO');
        expect(tableData[1]).toHaveTextContent('Arboviral');
        expect(tableData[2]).toHaveTextContent('ARBO');
        expect(tableData[3]).toHaveTextContent('12/31/2014');
    });
});
describe('Concept component tests', () => {
    it('should render a grid with 5 inputs labels which are Local code, UI Display name, Concept Code, Always Effective and Effective Until', () => {
        const { getByText } = render(
            <AlertProvider>
                <Concept />
            </AlertProvider>
        );
        expect(
            getByText('No value set concept is displayed. Please click the button below to add new value set concept.')
        ).toBeInTheDocument();
    });
});
