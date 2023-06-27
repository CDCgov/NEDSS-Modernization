import React from 'react';

const pagesLibrary = [
    {
        pageName: 'COVID-19 v1.1',
        eventType: 'Investigation',
        relatedConditions: '2019 Novel Coronavirus (11065)',
        status: 'Draft',
        lastUpdatedBy: 'PKS PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Babesiosis Investigation',
        eventType: 'Investigation',
        relatedConditions: 'Babesiosis (12010)',
        status: 'Published',
        lastUpdatedBy: 'PKS PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Hepatitis A Acute Investigation',
        eventType: 'Investigation',
        relatedConditions: 'Hepatitis A, acute (10110)',
        status: 'Published',
        lastUpdatedBy: 'PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Malaria Investigation',
        eventType: 'Investigation',
        relatedConditions: 'Malaria (10130)',
        status: 'Published',
        lastUpdatedBy: 'PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'STD HIV Interview Record',
        eventType: 'Investigation',
        relatedConditions: 'Chancroid (10273)',
        status: 'Published',
        lastUpdatedBy: 'PKS PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'TB LTBI Investigation',
        eventType: 'Investigation',
        relatedConditions: 'Tuberculosis (2020 RVCT) (102201)',
        status: 'Draft',
        lastUpdatedBy: 'PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Mumps Investigation',
        eventType: 'Investigation',
        relatedConditions: 'Mumps (10180)',
        status: 'Published',
        lastUpdatedBy: 'PKS PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Generic Contact Record',
        eventType: 'Investigation',
        relatedConditions: 'Hepatitis B, acute (10100)',
        status: 'Draft',
        lastUpdatedBy: 'PKS PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Generic V2 Investigation',
        eventType: 'Investigation',
        relatedConditions: 'Burcellosis (10020)',
        status: 'Draft',
        lastUpdatedBy: 'PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    },
    {
        pageName: 'Carbon Monoxide Investgation',
        eventType: 'Investigation',
        relatedConditions: 'Carbon Monoxide',
        status: 'Published',
        lastUpdatedBy: 'PKS',
        lastUpdatedDate: '2012-04-23T18:25:43.511Z',
        template: 'Gen v2'
    }
];
const conditionsLibrary = [
    {
        condition_name: 'BLAH'
    }
];
const templatesLibrary = [{}];
const valueSetsLibrary = [{}];
const questionsLibrary = [{}];

export interface PageBuilderContextProps {
    pages: any[];
    conditions: any[];
    templates: any[];
    valueSets: any[];
    questions: any[];
    children?: any;
}

export const PageBuilderContext = React.createContext<PageBuilderContextProps>({
    pages: pagesLibrary,
    conditions: conditionsLibrary,
    templates: templatesLibrary,
    valueSets: valueSetsLibrary,
    questions: questionsLibrary
});

export const PageBuilderProvider = ({
    pages,
    conditions,
    templates,
    valueSets,
    questions,
    children
}: PageBuilderContextProps) => {
    return (
        <PageBuilderContext.Provider value={{ pages, conditions, templates, valueSets, questions }}>
            {children}
        </PageBuilderContext.Provider>
    );
};
