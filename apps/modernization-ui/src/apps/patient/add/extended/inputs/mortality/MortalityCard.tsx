import React from 'react';
import { Card } from '../../card/Card';
import { MortalityEntryFields } from 'apps/patient/data/mortality/MortalityEntryFields';

export const MortalityCard = () => {
    return (
        <Card id="mortality" title="Mortality">
            <MortalityEntryFields />
        </Card>
    );
};
