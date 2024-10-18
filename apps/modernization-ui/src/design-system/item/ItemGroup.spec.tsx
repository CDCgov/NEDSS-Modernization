import React from 'react';
import { render } from '@testing-library/react';
import { ItemGroup } from './ItemGroup';

describe('ItemGroup Component', () => {
    it('renders without crashing', () => {
        const { getByText } = render(<ItemGroup>Test Children</ItemGroup>);
        expect(getByText('Test Children')).toBeDefined();
    });

    it('renders with a header label', () => {
        const { getByText } = render(
            <ItemGroup label="Test Label">
                <span>Test Child</span>
            </ItemGroup>
        );
        expect(getByText('Test Label')).toBeDefined();
    });

    it('does not render a header when label is not provided', () => {
        const result = render(
            <ItemGroup>
                <span>Test Child</span>
            </ItemGroup>
        );
        const header = result.baseElement.querySelector('header');
        expect(header).toBeNull();
    });

    it('renders with a data-item-type attribute when type is provided', () => {
        const result = render(
            <ItemGroup type="address">
                <span>Test Child</span>
            </ItemGroup>
        );

        const div = result.baseElement.querySelector('div.itemgroup');
        expect(div).toHaveAttribute('data-item-type', 'address');
    });

    it('renders with a <address> element when type is addressable', () => {
        const result = render(
            <ItemGroup type="address">
                123 Happy St <br />
                Farmington, NM 83640
            </ItemGroup>
        );

        const addrElement = result.baseElement.querySelector('div.itemgroup > address');
        expect(addrElement).toBeDefined();
    });

    it('renders with a <p> element when type is not addressable', () => {
        const result = render(<ItemGroup type="other">Some information here</ItemGroup>);

        const pElement = result.baseElement.querySelector('div.itemgroup > p');
        expect(pElement).toBeDefined();
        expect(pElement).toHaveAttribute('role', 'group');
    });
});
