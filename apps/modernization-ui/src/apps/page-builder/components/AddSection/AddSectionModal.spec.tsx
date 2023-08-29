import { render } from '@testing-library/react';

import React from 'react';
import AddSectionModal from './AddSectionModal';

const props = {
    modalRef: { current: null },
    tabId: 5,
    pageId: '10056'
};

describe('AddSectionModal', () => {
    it('should render successfully', () => {
        const { baseElement } = render(<AddSectionModal {...props} />);
        expect(baseElement).toBeTruthy();
    });

    it('should have the heading with the text "Manage Sections"', () => {
        const { getByText } = render(<AddSectionModal {...props} />);
        expect(getByText('Manage Sections')).toBeTruthy();
    });

    it('should have a label with the text "Section name"', () => {
        const { getByText } = render(<AddSectionModal {...props} />);
        expect(getByText('Section name')).toBeTruthy();
    });

    it('should have a label with the text "Section description"', () => {
        const { getByText } = render(<AddSectionModal {...props} />);
        expect(getByText('Section description')).toBeTruthy();
    });

    it('should have a text input with the label "section name"', () => {
        const { getByLabelText } = render(<AddSectionModal {...props} />);
        expect(getByLabelText('section name')).toBeTruthy();
    });

    it('should have a text input with the label "section description"', () => {
        const { getByLabelText } = render(<AddSectionModal {...props} />);
        expect(getByLabelText('section description')).toBeTruthy();
    });

    it('should have a toggle button with the label "Visible"', () => {
        const { getByLabelText } = render(<AddSectionModal {...props} />);
        expect(getByLabelText('visible')).toBeTruthy();
    });

    it('should have a button with the text "Cancel"', () => {
        const { getByText } = render(<AddSectionModal {...props} />);
        expect(getByText('Cancel')).toBeTruthy();
    });

    it('should have a button with the text "Add section"', () => {
        const { getByText } = render(<AddSectionModal {...props} />);
        expect(getByText('Add Section')).toBeTruthy();
    });
});
