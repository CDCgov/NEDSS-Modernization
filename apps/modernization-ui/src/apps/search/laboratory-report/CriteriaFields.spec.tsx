import { render } from '@testing-library/react';
import { MockedProvider } from '@apollo/react-testing';
import { FindDistinctCodedResultsDocument, FindDistinctResultedTestDocument } from 'generated/graphql/schema';

import { CriteriaFields } from './CriteriaFields';
import { LabratorySearchCriteriaFormWrapper } from './LabratorySearchCriteriaFormWrapper';

const WithAPIMocks = () => {
    const mocks = [
        {
            request: {
                query: FindDistinctCodedResultsDocument,
                variables: {
                    searchText: ''
                }
            },
            result: {
                data: {
                    findDistinctCodedResults: []
                }
            }
        },
        {
            request: {
                query: FindDistinctResultedTestDocument,
                variables: {
                    searchText: ''
                }
            },
            result: {
                data: {
                    findDistinctResultedTests: []
                }
            }
        }
    ];

    return (
        <MockedProvider mocks={mocks} addTypename={false}>
            <LabratorySearchCriteriaFormWrapper>
                <CriteriaFields />
            </LabratorySearchCriteriaFormWrapper>
        </MockedProvider>
    );
};

describe('when displaying Laboratory Search Criteria within the Criteria section ', () => {
    it('should allow search for Resulted test', () => {
        const { getByRole } = render(<WithAPIMocks />);

        expect(getByRole('textbox', { name: 'Resulted test' })).toBeInTheDocument();

        expect(getByRole('textbox', { name: 'Coded result/organism' })).toBeInTheDocument();
    });

    it('should allow search for Coded result/organism', () => {
        const { getByRole } = render(<WithAPIMocks />);

        expect(getByRole('textbox', { name: 'Coded result/organism' })).toBeInTheDocument();
    });
});
