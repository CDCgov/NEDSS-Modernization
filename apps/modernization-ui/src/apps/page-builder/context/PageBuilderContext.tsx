import { Outlet } from 'react-router-dom';
import { QuestionProvider } from './QuestionsContext';
import { ConditionProvider } from './ConditionsContext';
import { ValueSetsProvider } from './ValueSetContext';
import { BusinessRuleProvider } from './BusinessContext';
import { ConceptsProvider } from './ConceptContext';

const PageBuilderContextProvider = () => {
    return (
        // Wrap other Providers here: Questions, Value Sets, Templates et al.

        <QuestionProvider>
            <ConditionProvider>
                <ValueSetsProvider>
                    <BusinessRuleProvider>
                        <ConceptsProvider>
                            <Outlet />
                        </ConceptsProvider>
                    </BusinessRuleProvider>
                </ValueSetsProvider>
            </ConditionProvider>
        </QuestionProvider>
    );
};

export default PageBuilderContextProvider;
