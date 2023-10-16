import { QuestionLibraryTable } from './QuestionLibraryTable';
import { render, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const questionsSummary: any = {};
        const summaries = [questionsSummary];
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <QuestionLibraryTable summaries={summaries} />
                </AlertProvider>
            </BrowserRouter>
        );

        const tableHeads = container.getElementsByClassName('table-head');

        await waitFor(() => {
            expect(tableHeads[1].textContent).toBe('Type');
            expect(tableHeads[2].textContent).toBe('Unique ID');
            expect(tableHeads[3].textContent).toBe('Unique name');
        });
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
            <AlertProvider>
                <QuestionLibraryTable summaries={summaries} />
            </AlertProvider>
        );

        const tableData = container.getElementsByClassName('table-data');

        await waitFor(() => {
            expect(tableData[1]).toHaveTextContent('TEXT');
            expect(tableData[2]).toHaveTextContent('INV118');
            expect(tableData[3]).toHaveTextContent('Reporting Source Zip');
            expect(tableData[4]).toHaveTextContent('INV');
        });
    });
});
