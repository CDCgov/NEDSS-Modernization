import React from 'react';
import { ActionProps } from 'react-querybuilder';
import { Button } from '../../../../../design-system/button';

const AdvancedFilterButton = (props: ActionProps) => {
    switch (props.className) {
        case 'ruleGroup-addRule':
        case 'ruleGroup-addGroup':
            return (
                <Button
                    type={'button'}
                    onClick={(e) => props.handleOnClick(e)}
                    secondary={props.className === 'ruleGroup-addGroup'}
                    className={props.className}
                >
                    {props.title}
                </Button>
            );
        case 'ruleGroup-remove':
        case 'rule-remove':
            return (
                <Button
                    icon="delete"
                    type={'button'}
                    className={'trash-icon'}
                    tertiary
                    sizing={'small'}
                    aria-label={props.title}
                    onClick={(e) => props.handleOnClick(e)}
                />
            );
    }
};

export { AdvancedFilterButton };
