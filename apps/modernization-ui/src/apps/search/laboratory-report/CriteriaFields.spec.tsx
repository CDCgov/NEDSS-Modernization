import { render } from '@testing-library/react';

import { CriteriaFields } from './CriteriaFields';
import { LabratorySearchCriteriaFormWrapper } from './LabratorySearchCriteriaFormWrapper';

describe('when displaying Laboratory Search Criteria within the Criteria section ', () => {
    it('should allow search for Resulted test', () => {
        const { getByRole } = render(<CriteriaFields />, { wrapper: LabratorySearchCriteriaFormWrapper });

        expect(getByRole('textbox', { name: 'Resulted test' })).toBeInTheDocument();

        expect(getByRole('textbox', { name: 'Coded result/organism' })).toBeInTheDocument();
    });

    it('should allow search for Coded result/organism', () => {
        const { getByRole } = render(<CriteriaFields />, { wrapper: LabratorySearchCriteriaFormWrapper });

        expect(getByRole('textbox', { name: 'Coded result/organism' })).toBeInTheDocument();
    });
});
