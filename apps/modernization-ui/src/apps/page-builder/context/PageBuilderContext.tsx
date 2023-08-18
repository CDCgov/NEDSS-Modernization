import { Outlet } from 'react-router-dom';
import { PagesProvider } from './PagesContext';
import { QuestionProvider } from './QuestionsContext';
import { ValueSetsProvider } from './ValueSetContext';

import { ConceptsProvider } from './ConceptContext';
const PageBuilderContextProvider = () => {
    return (
        // Wrap other Providers here: Questions, Value Sets, Templates et al.
        <PagesProvider>
            <QuestionProvider>
                <ValueSetsProvider>
                    <ConceptsProvider>
                        <Outlet />
                    </ConceptsProvider>
                </ValueSetsProvider>
            </QuestionProvider>
        </PagesProvider>
    );
};

export default PageBuilderContextProvider;
