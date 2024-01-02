import { AlertProvider } from "alert";
import { PagesQuestion, PagesResponse, PagesSection, PagesSubSection, PagesTab } from "apps/page-builder/generated";
import { BrowserRouter } from "react-router-dom";
import { PageManagementProvider } from "../../usePageManagement";
import { render } from "@testing-library/react";
import { EditPage } from "apps/page-builder/pages/EditPage/EditPage";
import { PageContent } from "../content/PageContent";


const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft'
};

const fetch = () => {
    jest.fn();
}

const dateQuestion: PagesQuestion = {
    id: 3,
    name: "date test question",
    order: 3,
    dataType: 'DATE',
    isStandard: true
}

const dropDownQuestion: PagesQuestion = {
    id: 4,
    name: "test drop down question",
    order: 4,
    isStandard: true,
    displayComponent: 1007
}

const subSections: PagesSubSection = {
    id: 2,
    name: "test subsection",
    order: 2,
    questions: [dateQuestion, dropDownQuestion],
    visible: true
}

const sections: PagesSection = {
    id: 1,
    name: "test section",
    order: 1,
    subSections: [subSections],
    visible: true
}

const tabs: PagesTab = {
    id: 0,
    name: "test tab",
    order: 0,
    sections: [sections],
    visible: true
}




describe('when page loads', () => {
    it('date input should have calendar next to input field', () => {
        const {getByTestId} = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <PageContent tab={tabs}/>
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByTestId('calendar-icon')).toBeInTheDocument();

    });

    it('drop down input should be true for display component 1007', () => {
        const {getByTestId} = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <PageContent tab={tabs}/>
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByTestId('dropdown-input')).toBeInTheDocument();
    })
})