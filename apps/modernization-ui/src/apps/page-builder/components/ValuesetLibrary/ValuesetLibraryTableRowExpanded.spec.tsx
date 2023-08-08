import { PageProvider } from 'page';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { screen, render, fireEvent } from '@testing-library/react';
import { ValueSet } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';
import ValuesetLibraryTableRowExpanded from './ValuesetLibraryTableRowExpanded';

describe('when rendered', () => {
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

        expect(tabSection[0]).toBeInTheDocument();
    });

    describe('tab button section', () => {
        it('has 2 buttons with correct text', () => {
            const { container } = render(
                <BrowserRouter>
                    <PageProvider>
                        <AlertProvider>
                            <ValuesetLibraryTableRowExpanded data={{}} />
                        </AlertProvider>
                    </PageProvider>
                </BrowserRouter>
            );

            const tabSection = container.getElementsByClassName('tabSection');

            const buttons = tabSection[0].getElementsByClassName('tab');

            expect(buttons.length).toEqual(2);
            expect(buttons[0]).toHaveTextContent('Value set details');
            expect(buttons[1]).toHaveTextContent('Value set concepts');
        });
    });

    describe('valueSetDetailsSection', () => {
        it('is in the document', () => {
            const { container } = render(
                <BrowserRouter>
                    <PageProvider>
                        <AlertProvider>
                            <ValuesetLibraryTableRowExpanded data={expectedValueSet} />
                        </AlertProvider>
                    </PageProvider>
                </BrowserRouter>
            );

            const detailsSection = container.getElementsByClassName('valuesetDetailsSection');

            expect(detailsSection[0]).toBeInTheDocument();
        });

        it('has 4 details containers', () => {
            const { container } = render(
                <BrowserRouter>
                    <PageProvider>
                        <AlertProvider>
                            <ValuesetLibraryTableRowExpanded data={expectedValueSet} />
                        </AlertProvider>
                    </PageProvider>
                </BrowserRouter>
            );

            const detailsSection = container.getElementsByClassName('valuesetDetailsSection');
            const detailContainer = detailsSection[0].getElementsByClassName('detailContainer');

            expect(detailContainer.length).toEqual(4);
        });
    });
});
