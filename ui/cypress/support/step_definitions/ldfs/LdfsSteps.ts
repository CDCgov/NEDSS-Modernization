import { Given, Then, When } from 'cypress-cucumber-preprocessor/steps';
import AddHomePageLdfPage from '../../pages/system-management/page-management/AddHomePageLdfPage';
import ManageHomePageLdfPage from '../../pages/system-management/page-management/ManageHomePageLdfPage';
import PreviewHomePageLdfPage from '../../pages/system-management/page-management/PreviewHomePageLdfPage';

export interface HomePageLdf {
    type: 'Hyperlink' | 'Subheading (for display only)' | 'Comments (Read only text)';
    label: string;
    displayOrder: string;
    comment: string;
    linkUrl?: string;
    tooltip?: string;
}

function getHomePageLdfs(): HomePageLdf[] {
    return [
        {
            type: 'Comments (Read only text)',
            label: "test comment label \\&<>'",
            displayOrder: '1',
            comment: "test comment \\&<>'",
            tooltip: "test comment tooltip \\&<>'"
        },
        {
            type: 'Hyperlink',
            label: "test hyperlink label \\&<>'",
            displayOrder: '2',
            comment: "test hyperlink comments \\&<>'",
            linkUrl: "test \\&<>' hyperlink url(https://www.google.com) "
        }
    ];
}

Given(/Home Page locally defined fields exist/, () => {
    const homePageLdfs = getHomePageLdfs();
    const manageHomePageLdfPage = new ManageHomePageLdfPage();
    manageHomePageLdfPage.navigateTo();
    manageHomePageLdfPage.getLocalFieldLabels().then((labels) => {
        for (let ldf of homePageLdfs) {
            if (labels.includes(ldf.label)) {
                // ldf with label already exists, edit to make sure fields are correct
                manageHomePageLdfPage.clickEditWithMatchingLabel(ldf.label).then((editPage) => {
                    editPage.updateLdf(ldf);
                });
            } else {
                // ldf with label does not exist, create it
                const addHomePageLdfPage = new AddHomePageLdfPage();
                addHomePageLdfPage.navigateTo();
                addHomePageLdfPage.createLdf(ldf);
            }
        }
    });
});

Then('I can see the preview the home page locally defined fields', () => {
    const ldfs = getHomePageLdfs();
    const previewHomePageLdfPage = new PreviewHomePageLdfPage();
    previewHomePageLdfPage.navigateTo();
    for (let ldf of ldfs) {
        const ldfSection = previewHomePageLdfPage.getLdfSection();
        ldfSection.should('contain', ldf.label);
        if (ldf.type === 'Hyperlink') {
            const href = ldf.linkUrl?.substring(ldf.linkUrl.indexOf('(') + 1, ldf.linkUrl.indexOf(')'));
            const linkText = ldf.linkUrl?.substring(0, ldf.linkUrl.indexOf('(')) ?? '';
            ldfSection.contains(linkText).invoke('attr', 'href').should('equal', href);
        } else {
            ldfSection.contains(ldf.label).invoke('attr', 'title').should('equal', ldf.tooltip);
        }
    }
});

Then('I can delete home page locally defined fields', () => {
    const manageHomePageLdfPage = new ManageHomePageLdfPage();
    manageHomePageLdfPage.navigateTo();
    manageHomePageLdfPage.deleteAllLdfs().then(() => {
        manageHomePageLdfPage.getLocalFieldLabels().then((labels) => {
            expect(labels.length).equal(0);
        });
    });
});
