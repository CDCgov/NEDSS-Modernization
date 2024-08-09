import { Button, Grid } from '@trussworks/react-uswds';
import { FormProvider, useForm } from 'react-hook-form';
import { Form } from 'react-router-dom';
import Administrative from './administrative/Administrative';
import styles from './extended.module.scss';
import classNames from 'classnames';

export const AddPatientExtended = () => {
    const methods = useForm({
        mode: 'onBlur'
    });

    return (
        <Grid row>
            <Grid col={3} className={styles.sidebar}>
                Sidebar
            </Grid>
            <Grid col={9}>
                <FormProvider {...methods}>
                    <Form autoComplete="off">
                        <Grid row className={styles['page-title-bar']}>
                            <div className={classNames(styles.flexBox, 'flex-justify width-full')}>
                                <h1>New patient</h1>
                                <Button type={'submit'}>Save changes</Button>
                            </div>
                        </Grid>
                        <div>
                            <Grid row className={classNames('padding-3')}>
                                <Grid col={9}>
                                    <Administrative title="Administrative" id={'section-Administrative'} />
                                </Grid>
                            </Grid>
                        </div>
                    </Form>
                </FormProvider>
            </Grid>
        </Grid>
    );
};
