import { Dropdown, ErrorMessage, FormGroup, Label, TextInput } from '@trussworks/react-uswds';
import { useState } from 'react';

export interface InputAddressFields {
    streetAddress1: string;
    streetAddress2: string;
    city: string;
    state: string;
    zip: string;
    county: string;
    censusTract: string;
    country: string;
}
export default function AddressFields({
    addressFields,
    updateCallback
}: {
    addressFields: InputAddressFields;
    updateCallback: (inputNameFields: InputAddressFields) => void;
}) {
    const [isTractValid, setIsTractValid] = useState(true);

    function updateTractValidity() {
        setIsTractValid((document.getElementById('census-tract') as HTMLInputElement).validity.valid);
    }
    return (
        <>
            <Label htmlFor="mailing-address-1">Street address 1</Label>
            <TextInput
                id="mailing-address-1"
                name="mailing-address-1"
                type="text"
                defaultValue={addressFields.streetAddress1}
                onChange={(v) => updateCallback({ ...addressFields, streetAddress1: v.target.value })}
            />

            <Label htmlFor="mailing-address-2" hint=" (optional)">
                Street address 2
            </Label>
            <TextInput
                id="mailing-address-2"
                name="mailing-address-2"
                type="text"
                defaultValue={addressFields.streetAddress2}
                onChange={(v) => updateCallback({ ...addressFields, streetAddress2: v.target.value })}
            />

            <div className="grid-row grid-gap">
                <div className="mobile-lg:grid-col-7">
                    <Label htmlFor="city">City</Label>
                    <TextInput
                        id="city"
                        name="city"
                        type="text"
                        defaultValue={addressFields.city}
                        onChange={(v) => updateCallback({ ...addressFields, city: v.target.value })}
                    />
                </div>
                <div className="mobile-lg:grid-col-5">
                    <Label htmlFor="state">State</Label>
                    <Dropdown
                        id="state"
                        name="state"
                        defaultValue={addressFields.state}
                        onChange={(v) => updateCallback({ ...addressFields, state: v.target.value })}>
                        <option></option>
                        <option value="AL">Alabama</option>
                        <option value="AK">Alaska</option>
                        <option value="AZ">Arizona</option>
                        <option value="AR">Arkansas</option>
                        <option value="CA">California</option>
                        <option value="CO">Colorado</option>
                        <option value="CT">Connecticut</option>
                        <option value="DE">Delaware</option>
                        <option value="DC">District of Columbia</option>
                        <option value="FL">Florida</option>
                        <option value="GA">Georgia</option>
                        <option value="HI">Hawaii</option>
                        <option value="ID">Idaho</option>
                        <option value="IL">Illinois</option>
                        <option value="IN">Indiana</option>
                        <option value="IA">Iowa</option>
                        <option value="KS">Kansas</option>
                        <option value="KY">Kentucky</option>
                        <option value="LA">Louisiana</option>
                        <option value="ME">Maine</option>
                        <option value="MD">Maryland</option>
                        <option value="MA">Massachusetts</option>
                        <option value="MI">Michigan</option>
                        <option value="MN">Minnesota</option>
                        <option value="MS">Mississippi</option>
                        <option value="MO">Missouri</option>
                        <option value="MT">Montana</option>
                        <option value="NE">Nebraska</option>
                        <option value="NV">Nevada</option>
                        <option value="NH">New Hampshire</option>
                        <option value="NJ">New Jersey</option>
                        <option value="NM">New Mexico</option>
                        <option value="NY">New York</option>
                        <option value="NC">North Carolina</option>
                        <option value="ND">North Dakota</option>
                        <option value="OH">Ohio</option>
                        <option value="OK">Oklahoma</option>
                        <option value="OR">Oregon</option>
                        <option value="PA">Pennsylvania</option>
                        <option value="RI">Rhode Island</option>
                        <option value="SC">South Carolina</option>
                        <option value="SD">South Dakota</option>
                        <option value="TN">Tennessee</option>
                        <option value="TX">Texas</option>
                        <option value="UT">Utah</option>
                        <option value="VT">Vermont</option>
                        <option value="VA">Virginia</option>
                        <option value="WA">Washington</option>
                        <option value="WV">West Virginia</option>
                        <option value="WI">Wisconsin</option>
                        <option value="WY">Wyoming</option>
                    </Dropdown>
                </div>
            </div>
            <div className="grid-row grid-gap">
                <div className="mobile-lg:grid-col-5">
                    <Label htmlFor="zip">ZIP</Label>
                    <TextInput
                        id="zip"
                        name="zip"
                        type="text"
                        inputSize="medium"
                        pattern="[\d]{5}(-[\d]{4})?"
                        defaultValue={addressFields.zip}
                        onChange={(v) => updateCallback({ ...addressFields, zip: v.target.value })}
                    />
                </div>
                <div className="mobile-lg:grid-col-7">
                    <Label htmlFor="county">County</Label>
                    <Dropdown
                        id="county"
                        name="county"
                        defaultValue={addressFields.county}
                        onChange={(v) => updateCallback({ ...addressFields, county: v.target.value })}>
                        <option value=""></option>
                        <option value="">NYI</option>
                    </Dropdown>
                </div>
            </div>
            <FormGroup error={!isTractValid}>
                <Label htmlFor="census-tract" error={!isTractValid}>
                    Census Tract
                </Label>
                <TextInput
                    id="census-tract"
                    name="census-tract"
                    type="text"
                    pattern="[0-9]{4}(.(([0-8][0-9])|([9][0-8])))?"
                    onBlur={() => updateTractValidity()}
                    defaultValue={addressFields.censusTract}
                    onChange={(v) => updateCallback({ ...addressFields, censusTract: v.target.value })}
                />
                {!isTractValid ? (
                    <ErrorMessage>
                        Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is
                        the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.
                    </ErrorMessage>
                ) : (
                    ''
                )}
            </FormGroup>
            <Label htmlFor="country">Country</Label>
            <Dropdown
                id="country"
                name="country"
                defaultValue={addressFields.country}
                onChange={(v) => updateCallback({ ...addressFields, country: v.target.value })}>
                <option value=""></option>
                <option value="840">United States</option>
                <option value="4">Afghanistan</option>
                <option value="8">Albania</option>
                <option value="12">Algeria</option>
                <option value="16">American Samoa</option>
                <option value="20">Andorra</option>
                <option value="24">Angola</option>
                <option value="660">Anguilla</option>
                <option value="28">Antigua and Barbuda</option>
                <option value="32">Argentina</option>
                <option value="51">Armenia</option>
                <option value="533">Aruba</option>
                <option value="36">Australia</option>
                <option value="40">Austria</option>
                <option value="31">Azerbaijan</option>
                <option value="44">Bahamas</option>
                <option value="48">Bahrain</option>
                <option value="50">Bangladesh</option>
                <option value="52">Barbados</option>
                <option value="112">Belarus</option>
                <option value="56">Belgium</option>
                <option value="84">Belize</option>
                <option value="204">Benin</option>
                <option value="60">Bermuda</option>
                <option value="64">Bhutan</option>
                <option value="68">Bolivia</option>
                <option value="70">Bosnia and Herzegovina</option>
                <option value="72">Botswana</option>
                <option value="76">Brazil</option>
                <option value="92">British Virgin Islands</option>
                <option value="96">Brunei Darussalam</option>
                <option value="100">Bulgaria</option>
                <option value="854">Burkina Faso</option>
                <option value="108">Burundi</option>
                <option value="116">Cambodia</option>
                <option value="120">Cameroon</option>
                <option value="124">Canada</option>
                <option value="132">Cape Verde</option>
                <option value="136">Cayman Islands</option>
                <option value="140">Central African Republic</option>
                <option value="148">Chad</option>
                <option value="830">Channel Islands</option>
                <option value="152">Chile</option>
                <option value="156">China</option>
                <option value="170">Colombia</option>
                <option value="174">Comoros</option>
                <option value="178">Congo</option>
                <option value="184">Cook Islands</option>
                <option value="188">Costa Rica</option>
                <option value="384">Cote d'Ivoire</option>
                <option value="191">Croatia</option>
                <option value="192">Cuba</option>
                <option value="196">Cyprus</option>
                <option value="203">Czech Republic</option>
                <option value="408">Democratic People's Republic of Korea</option>
                <option value="180">Democratic Republic of the Congo</option>
                <option value="208">Denmark</option>
                <option value="262">Djibouti</option>
                <option value="212">Dominica</option>
                <option value="214">Dominican Republic</option>
                <option value="626">East Timor</option>
                <option value="218">Ecuador</option>
                <option value="818">Egypt</option>
                <option value="222">El Salvador</option>
                <option value="226">Equatorial Guinea</option>
                <option value="232">Eritrea</option>
                <option value="233">Estonia</option>
                <option value="231">Ethiopia</option>
                <option value="234">Faeroe Islands</option>
                <option value="238">Falkland Islands (Malvinas)</option>
                <option value="242">Fiji</option>
                <option value="246">Finland</option>
                <option value="250">France</option>
                <option value="254">French Guiana</option>
                <option value="258">French Polynesia</option>
                <option value="266">Gabon</option>
                <option value="270">Gambia</option>
                <option value="268">Georgia</option>
                <option value="276">Germany</option>
                <option value="288">Ghana</option>
                <option value="292">Gibraltar</option>
                <option value="300">Greece</option>
                <option value="304">Greenland</option>
                <option value="308">Grenada</option>
                <option value="312">Guadeloupe</option>
                <option value="316">Guam</option>
                <option value="320">Guatemala</option>
                <option value="324">Guinea</option>
                <option value="624">Guinea-Bissau</option>
                <option value="328">Guyana</option>
                <option value="332">Haiti</option>
                <option value="336">Holy See</option>
                <option value="340">Honduras</option>
                <option value="344">Hong Kong Special Administrative Region of China</option>
                <option value="348">Hungary</option>
                <option value="352">Iceland</option>
                <option value="356">India</option>
                <option value="360">Indonesia</option>
                <option value="364">Iran (Islamic Republic of)</option>
                <option value="368">Iraq</option>
                <option value="372">Ireland</option>
                <option value="833">Isle of Man</option>
                <option value="376">Israel</option>
                <option value="380">Italy</option>
                <option value="388">Jamaica</option>
                <option value="392">Japan</option>
                <option value="400">Jordan</option>
                <option value="398">Kazakhstan</option>
                <option value="404">Kenya</option>
                <option value="296">Kiribati</option>
                <option value="414">Kuwait</option>
                <option value="417">Kyrgyzstan</option>
                <option value="418">Lao People's Democratic Republic</option>
                <option value="428">Latvia</option>
                <option value="422">Lebanon</option>
                <option value="426">Lesotho</option>
                <option value="430">Liberia</option>
                <option value="434">Libyan Arab Jamahiriya</option>
                <option value="438">Liechtenstein</option>
                <option value="440">Lithuania</option>
                <option value="442">Luxembourg</option>
                <option value="446">Macao Special Administrative Region of China</option>
                <option value="450">Madagascar</option>
                <option value="454">Malawi</option>
                <option value="458">Malaysia</option>
                <option value="462">Maldives</option>
                <option value="466">Mali</option>
                <option value="470">Malta</option>
                <option value="584">Marshall Islands</option>
                <option value="474">Martinique</option>
                <option value="478">Mauritania</option>
                <option value="480">Mauritius</option>
                <option value="484">Mexico</option>
                <option value="583">Micronesia, Federated States of</option>
                <option value="492">Monaco</option>
                <option value="496">Mongolia</option>
                <option value="500">Montserrat</option>
                <option value="504">Morocco</option>
                <option value="508">Mozambique</option>
                <option value="104">Myanmar</option>
                <option value="516">Namibia</option>
                <option value="520">Nauru</option>
                <option value="524">Nepal</option>
                <option value="528">Netherlands</option>
                <option value="530">Netherlands Antilles</option>
                <option value="540">New Caledonia</option>
                <option value="554">New Zealand</option>
                <option value="558">Nicaragua</option>
                <option value="562">Niger</option>
                <option value="566">Nigeria</option>
                <option value="570">Niue</option>
                <option value="574">Norfolk Island</option>
                <option value="580">Northern Mariana Islands</option>
                <option value="578">Norway</option>
                <option value="275">Occupied Palestinian Territory</option>
                <option value="512">Oman</option>
                <option value="586">Pakistan</option>
                <option value="585">Palau</option>
                <option value="591">Panama</option>
                <option value="598">Papua New Guinea</option>
                <option value="600">Paraguay</option>
                <option value="604">Peru</option>
                <option value="608">Philippines</option>
                <option value="612">Pitcairn</option>
                <option value="616">Poland</option>
                <option value="620">Portugal</option>
                <option value="630">Puerto Rico</option>
                <option value="634">Qatar</option>
                <option value="410">Republic of Korea</option>
                <option value="498">Republic of Moldova</option>
                <option value="638">Réunion</option>
                <option value="642">Romania</option>
                <option value="643">Russian Federation</option>
                <option value="646">Rwanda</option>
                <option value="654">Saint Helena</option>
                <option value="659">Saint Kitts and Nevis</option>
                <option value="662">Saint Lucia</option>
                <option value="666">Saint Pierre and Miquelon</option>
                <option value="670">Saint Vincent and the Grenadines</option>
                <option value="882">Samoa</option>
                <option value="674">San Marino</option>
                <option value="678">Sao Tome and Principe</option>
                <option value="682">Saudi Arabia</option>
                <option value="686">Senegal</option>
                <option value="690">Seychelles</option>
                <option value="694">Sierra Leone</option>
                <option value="702">Singapore</option>
                <option value="703">Slovakia</option>
                <option value="705">Slovenia</option>
                <option value="90">Solomon Islands</option>
                <option value="706">Somalia</option>
                <option value="710">South Africa</option>
                <option value="724">Spain</option>
                <option value="144">Sri Lanka</option>
                <option value="736">Sudan</option>
                <option value="740">Suriname</option>
                <option value="744">Svalbard and Jan Mayen Islands</option>
                <option value="748">Swaziland</option>
                <option value="752">Sweden</option>
                <option value="756">Switzerland</option>
                <option value="760">Syrian Arab Republic</option>
                <option value="158">Taiwan Province of China</option>
                <option value="762">Tajikistan</option>
                <option value="764">Thailand</option>
                <option value="807">The former Yugoslav Republic of Macedonia</option>
                <option value="768">Togo</option>
                <option value="772">Tokelau</option>
                <option value="776">Tonga</option>
                <option value="780">Trinidad and Tobago</option>
                <option value="788">Tunisia</option>
                <option value="792">Turkey</option>
                <option value="795">Turkmenistan</option>
                <option value="796">Turks and Caicos Islands</option>
                <option value="798">Tuvalu</option>
                <option value="800">Uganda</option>
                <option value="804">Ukraine</option>
                <option value="784">United Arab Emirates</option>
                <option value="826">United Kingdom</option>
                <option value="834">United Republic of Tanzania</option>
                <option value="850">United States Virgin Islands</option>
                <option value="858">Uruguay</option>
                <option value="860">Uzbekistan</option>
                <option value="548">Vanuatu</option>
                <option value="862">Venezuela</option>
                <option value="704">Viet Nam</option>
                <option value="876">Wallis and Futuna Islands</option>
                <option value="732">Western Sahara</option>
                <option value="887">Yemen</option>
                <option value="891">Yugoslavia</option>
                <option value="894">Zambia</option>
                <option value="716">Zimbabwe</option>
            </Dropdown>
        </>
    );
}
