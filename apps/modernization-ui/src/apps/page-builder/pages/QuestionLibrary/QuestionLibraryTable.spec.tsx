import { PageProvider } from 'page';
import { QuestionLibraryTable } from './QuestionLibraryTable';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const questionsSummary: any = {};
        const summaries = [questionsSummary];
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <AlertProvider>
                        <QuestionLibraryTable summaries={summaries} />
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const tableHeads = container.getElementsByClassName('table-head');

        expect(tableHeads[0].textContent).toBe('Type');
        expect(tableHeads[1].textContent).toBe('Unique ID');
        expect(tableHeads[2].textContent).toBe('Unique name');
    });
});

describe('when at least one summary is available', () => {
    const questionsSummary: any = {
        id: 1006253,
        mask: 'Test',
        fieldSize: '10',
        defaultValue: null,
        codeSet: 'PHIN',
        uniqueId: 'INV118',
        uniqueName: 'Reporting Source Zip',
        status: 'Active',
        subgroup: 'INV',
        description: 'TestFromUI',
        type: 'TEXT',
        label: 'Test',
        tooltip: 'Test',
        displayControl: 0,
        adminComments: null,
        dataMartInfo: {
            defaultLabelInReport: 'Test',
            defaultRdbTableName: 'D_ORGANIZATION',
            rdbColumnName: 'ORGANIZATION_ZIP',
            dataMartColumnName: null
        },
        messagingInfo: {
            includedInMessage: false,
            messageVariableId: null,
            labelInMessage: null,
            codeSystem: '2.16.840.1.113883.6.1',
            requiredInMessage: false,
            hl7DataType: null
        }
    };
    const summaries = [questionsSummary];

    it('should display the questions summaries', async () => {
        const { container } = render(
            <PageProvider>
                <AlertProvider>
                    <QuestionLibraryTable summaries={summaries} />
                </AlertProvider>
            </PageProvider>
        );

        const tableData = container.getElementsByClassName('table-data');
        expect(tableData[0]).toHaveTextContent('TEXT');
        expect(tableData[1]).toHaveTextContent('INV118');
        expect(tableData[2]).toHaveTextContent('Reporting Source Zip');
        expect(tableData[3]).toHaveTextContent('INV');
    });
});
