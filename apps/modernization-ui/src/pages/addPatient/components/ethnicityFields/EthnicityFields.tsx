import { Fieldset, Grid, Radio } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';

export interface InputEthnicityFields {
    ethnicity: string;
    // TODO races
}
export default function EthnicityFields({ id, title }: { id?: string; title?: string }) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12}>
                        <Fieldset>
                            <Radio id="2135-2" name={'2135-2'} label={'Hispanic or Latino'} />
                            <Radio id="2186-5" name={'2186-5'} label={'Not Hispanic or Latino'} />
                            <Radio id="UNK" name={'UNK'} label={'Unknown'} />
                        </Fieldset>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
