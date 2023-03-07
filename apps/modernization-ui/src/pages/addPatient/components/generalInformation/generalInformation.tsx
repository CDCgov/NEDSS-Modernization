import { Grid, Label, Textarea } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';

export default function GeneralInformation({ id, title, control }: { id?: string; title?: string; control?: any }) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12} className="margin-top-2">
                        <span data-testid="required-text">All fields marked with</span>{' '}
                        <span className="text-red">*</span> are required
                    </Grid>
                    <Grid col={6}>
                        <Label htmlFor="asOf">
                            <span data-testid="date-lable">Information as of Date</span>{' '}
                            <span className="text-red">*</span>
                        </Label>
                        <Controller
                            control={control}
                            name="asOf"
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="asOf"
                                    htmlFor={'asOf'}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="additionalComments"
                            render={({ field: { onChange } }) => (
                                <>
                                    <Label htmlFor={'additionalComments'}>Comments</Label>
                                    <Textarea onChange={onChange} name="additionalComments" id={'additionalComments'} />
                                </>
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
