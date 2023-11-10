import { render } from '@testing-library/react';

import React from 'react';
import TargetQuestion from './TargetQuestion';

const props = {
    modalRef: { current: null },
    tabId: 5,
    pageId: '10056',
    onAddSection: jest.fn()
};

describe('AddSectionModal', () => {
    it('should render successfully', () => {
        const { baseElement } = render(<TargetQuestion {...props} />);
        expect(baseElement).toBeTruthy();
    });
});
