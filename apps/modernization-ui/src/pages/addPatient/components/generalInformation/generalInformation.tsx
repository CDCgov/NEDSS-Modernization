import { DatePicker, Grid, Label, Textarea } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';

export interface InputNameFields {
    firstName: string;
    middleName: string;
    lastName: string;
    suffix: string;
}
export default function GeneralInformation({ id, title }: { id?: string; title?: string }) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12} className="margin-top-2">
                        <span data-testid="required-text">All fields marked with</span>{' '}
                        <span className="text-red">*</span> are required
                    </Grid>
                    <Grid col={6}>
                        <Label htmlFor="as-of-date">
                            <span data-testid="date-lable">Information as of Date</span>{' '}
                            <span className="text-red">*</span>
                        </Label>
                        <DatePicker id="asOf" name="asOf" />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Label htmlFor={'additionalComments'}>
                            <span data-testid="comment-lable">Comments</span>
                        </Label>
                        <Textarea name="additionalComments" id={'additionalComments'} />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
