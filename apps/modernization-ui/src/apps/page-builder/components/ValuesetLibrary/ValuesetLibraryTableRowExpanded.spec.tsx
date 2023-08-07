import { PageProvider } from 'page';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { screen, render, fireEvent } from '@testing-library/react';
import { ValueSet } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';
import ValuesetLibraryTableRowExpanded from './ValuesetLibraryTableRowExpanded';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <AlertProvider>
                        <ValuesetLibraryTable summaries={[]} sortChange={() => {}}></ValuesetLibraryTable>
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Type');
        expect(tableHeads[1].innerHTML).toBe('Value set name');
        expect(tableHeads[2].innerHTML).toBe('Value set description');
    });
});

describe('when at least one summary is available', () => {
    const expectedValueSet = {
        addTime: undefined,
        addUserId: 4567789,
        adminComments: 'test comments',
        assigningAuthorityCd: undefined,
        assigningAuthorityDescTxt: undefined,
        classCd: undefined,
        codeSetDescTxt: undefined,
        codeSetGroupId: undefined,
        effectiveFromTime: undefined,
        effectiveToTime: undefined,
        isModifiableInd: undefined,
        ldfPicklistIndCd: undefined,
        name: 'test name',
        nbsUid: 789456123,
        parentIsCd: undefined,
        sourceDomainNm: 'testdomainname',
        sourceVersionTxt: 'test_version_text',
        statusCd: 'status_cd',
        statusToTime: undefined,
        valueSetCode: 'LOCAL',
        valueSetNm: 'test_set_name',
        valueSetOid: '443322',
        valueSetStatusCd: 'status_code',
        valueSetStatusTime: undefined,
        valueSetTypeCd: 'Type_CD'
    };

    it('has a section with a tab button group', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <AlertProvider>
                        <ValuesetLibraryTableRowExpanded data={expectedValueSet} />
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const tabSection = container.getElementsByClassName('tabSection');

        expect(tabSection).toBeInTheDocument();
    });
});
