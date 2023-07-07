import React from 'react';

const conditionsLibrary = [
    {
        condition_name: 'BLAH'
    }
];
const templatesLibrary = [{}];
const valueSetsLibrary = [{}];
const questionsLibrary = [{}];

export interface PageBuilderContextProps {
    conditions: any[];
    templates: any[];
    valueSets: any[];
    questions: any[];
    children?: any;
}

export const PageBuilderContext = React.createContext<PageBuilderContextProps>({
    conditions: conditionsLibrary,
    templates: templatesLibrary,
    valueSets: valueSetsLibrary,
    questions: questionsLibrary
});

export const PageBuilderProvider = ({
    conditions,
    templates,
    valueSets,
    questions,
    children
}: PageBuilderContextProps) => {
    return (
        <PageBuilderContext.Provider value={{ conditions, templates, valueSets, questions }}>
            {children}
        </PageBuilderContext.Provider>
    );
};
