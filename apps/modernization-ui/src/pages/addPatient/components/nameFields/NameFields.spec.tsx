import { render } from '@testing-library/react';
import NameFields from './NameFields';

describe('NameFields component tests', () => {
    it('should render user name fields', () => {
        const nameFields = {
            firstName: '',
            middleName: '',
            lastName: '',
            suffix: ''
        };
        const fun = jest.fn();
        const { container } = render(<NameFields nameFields={nameFields} updateCallback={fun} />);
        expect(container.getElementsByClassName('last-name')[0].innerHTML).toBe('Last');
    });

    it('should render user name label', () => {
        const nameFields = {
            firstName: '',
            middleName: '',
            lastName: '',
            suffix: ''
        };
        const fun = jest.fn();
        const { container } = render(<NameFields nameFields={nameFields} updateCallback={fun} />);
        expect(container.getElementsByClassName('first-name')[0].innerHTML).toBe('First');
    });
});
