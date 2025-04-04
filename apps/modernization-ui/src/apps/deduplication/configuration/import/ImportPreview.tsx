import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import React from 'react';

type Props = {
    onCancel: () => void;
};
export const ImportPreview = ({ onCancel }: Props) => {
    return (
        <header>
            <Heading level={1}>Preview configuration</Heading>
            <div>
                <Button secondary onClick={onCancel}>
                    Cancel
                </Button>
            </div>
        </header>
    );
};
