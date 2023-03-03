import { Grid, Label, TextInput } from "@trussworks/react-uswds";
import FormCard from "../../../../components/FormCard/FormCard";
import { SelectInput } from "../../../../components/FormInputs/SelectInput";
import { Suffix } from "../../../../generated/graphql/schema";

export interface InputNameFields {
  firstName: string;
  middleName: string;
  lastName: string;
  suffix: string;
}
export default function NameFields({
  nameFields,
  updateCallback,
  id,
  title,
}: {
  nameFields: InputNameFields;
  updateCallback: (inputNameFields: InputNameFields) => void;
  id?: string;
  title?: string;
}) {
  return (
    <FormCard id={id} title={title}>
      <Grid col={12} className="padding-x-3 padding-bottom-3">
        <Grid row>
          <Grid col={6}>
            <Label htmlFor="last-name">Last</Label>
            <TextInput
              placeholder="Doe"
              id="last-name"
              name="last-name"
              type="text"
              defaultValue={nameFields.lastName}
              onChange={(e) =>
                updateCallback({ ...nameFields, lastName: e.target.value })
              }
            />
          </Grid>
        </Grid>
        <Grid row>
          <Grid col={6}>
            <Label htmlFor="first-name">First</Label>
            <TextInput
              placeholder="John"
              id="first-name"
              name="first-name"
              type="text"
              defaultValue={nameFields.firstName}
              onChange={(e) =>
                updateCallback({ ...nameFields, firstName: e.target.value })
              }
            />
          </Grid>
        </Grid>
        <Grid row>
          <Grid col={6}>
            <Label htmlFor="middle-name">Middle</Label>
            <TextInput
              placeholder="James"
              id="middle-name"
              name="middle-name"
              type="text"
              defaultValue={nameFields.middleName}
              onChange={(e) =>
                updateCallback({ ...nameFields, middleName: e.target.value })
              }
            />
          </Grid>
        </Grid>
        <Grid row>
          <Grid col={6}>
            <SelectInput
              name="suffix"
              htmlFor={"suffix"}
              label="Suffix"
              options={Object.values(Suffix).map((suffix) => {
                return {
                  name: suffix,
                  value: suffix,
                };
              })}
            />
          </Grid>
        </Grid>
      </Grid>
    </FormCard>
  );
}
