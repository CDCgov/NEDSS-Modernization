import { Concept } from './Concept';
import { render } from '@testing-library/react';

describe('when Concept rendered', () => {
    const valueset = {
        valueSetTypeCd: 'LOCAL',
        valueSetCode: 'Test code',
        valueSetNm: 'Test name',
        codeSetDescTxt: 'Test desc text'
    }
    it('should display value set type code', () => {
        const { container } = render(
            <Concept valueset={valueset} />
        );
        const testNames = container.getElementsByClassName('details');
        expect(testNames[0]).toHaveTextContent('LOCAL');
    });    
    it('should display value set code', () => {
        const { container } = render(
            <Concept valueset={valueset} />
        );
        const testNames = container.getElementsByClassName('details');
        expect(testNames[1]).toHaveTextContent('Test code');
    });    
    it('should display value set name', () => {
        const { container } = render(
            <Concept valueset={valueset} />
        );
        const testNames = container.getElementsByClassName('details');
        expect(testNames[2]).toHaveTextContent('Test name');
    });    
    it('should display value set desc text', () => {
        const { container } = render(
            <Concept valueset={valueset} />
        );
        const testNames = container.getElementsByClassName('details');
        expect(testNames[3]).toHaveTextContent('Test desc text');
    });
});
