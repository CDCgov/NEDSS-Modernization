import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PageBuilder } from '../../PageBuilder/PageBuilder';
import styles from './AddBusinessRule.module.scss';
import { Breadcrumb } from 'breadcrumb';
import { Heading } from 'components/heading';
// import { useAlert } from 'alert';
import { useForm } from 'react-hook-form';
// import { UserContext } from 'user';
// import { PageRuleControllerService } from '../../../generated';
// import { useAlert } from 'alert';
// import { authorization } from 'authorization';

export type FormValues = {
    ruleFunction: string;
    sourceIdentifier: string;
    sourceText: string;
    comparator?: string;
    sourceValue: string[];
    targetValueIdentifier?: string[];
    targetType: string;
    ruleDescription: string;
    targetValueText?: string[];
    anySourceValue?: boolean;
};

const AddBusinessRule = () => {
    const navigate = useNavigate();
    // const { state } = useContext(UserContext);
    const form = useForm<FormValues>({
        defaultValues: { targetType: 'SUBSECTION', anySourceValue: false },
        mode: 'onChange'
    });
    const [selectedFieldType, setSelectedFieldType] = useState('');
    // const token = `Bearer ${state.getToken()}`;
    // const { pageId, ruleId } = useParams();
    // const { showAlert } = useAlert();

    const handleCancel = () => {
        navigate(-1);
    };

    const fieldTypeTab = [
        { name: 'Enable' },
        { name: 'Date Compare' },
        { name: 'Disable' },
        { name: 'Hide' },
        { name: 'Unhide' },
        { name: 'Require If' }
    ];

    return (
        <>
            <section className={styles.addBusinessRuleHeader}>
                <header>
                    <h2>Page builder</h2>
                </header>
            </section>
            <PageBuilder page="business-rules">
                <Breadcrumb start="../">business rules</Breadcrumb>
                <div className={styles.addRulesContainer}>
                    <div className={styles.addBusinessRuleTitle}>
                        <Heading level={3}>Add new business rule</Heading>
                    </div>

                    <Grid row style={{ border: '2px solid blue', width: '1050px' }}>
                        <Grid col={3} style={{ backgroundColor: 'red' }}>
                            <label className="input-label">Function</label>
                        </Grid>
                        <Grid col={9} style={{ backgroundColor: 'yellow' }}>
                            <ButtonGroup type="segmented">
                                {fieldTypeTab.map((field, index) => (
                                    <Button
                                        key={index}
                                        type="button"
                                        outline={field.name !== selectedFieldType}
                                        onClick={() => {
                                            setSelectedFieldType(field.name);
                                            form.setValue('ruleFunction', field.name);
                                        }}>
                                        {field.name}
                                    </Button>
                                ))}
                            </ButtonGroup>
                        </Grid>
                    </Grid>
                </div>
                <div className={styles.footerButtonsContainer}>
                    <Button type="button" outline onClick={handleCancel}>
                        Cancel
                    </Button>
                    <Button type="submit" className="lbr" disabled>
                        Add to Library
                    </Button>
                </div>
            </PageBuilder>
        </>
    );
};

export default AddBusinessRule;
