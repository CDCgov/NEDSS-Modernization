// import { BlockingCriteria, usePatientMatchContext } from "apps/dedup-config/context/PatientMatchContext";
// import { SingleSelect } from "design-system/select";
// import { Icon } from "@trussworks/react-uswds";
// import { availableMethods } from "apps/dedup-config/context/PatientMatchContext";
// import { Controller } from "react-hook-form";
// import React from "react";

// export const BlockingCriteriaRow = ({ criteria }: { criteria: BlockingCriteria }) => {
//     const options = availableMethods.map((method, index) => ({
//         name: method.name,
//         label: method.name,
//         value: method.value,
//         id: index
//     }));
//     const {blockingCriteria, setBlockingCriteria} = usePatientMatchContext();
//     return (
//         <div>
//             {criteria.field.label}
//             <Controller
//                 control={form.control}
//                 name={`blockingCriteria.${criteria.field.name}.method`}
//                 render={({ field: { onChange, value, name } }) => (
//                     <SingleSelect
//                         id={`method-select-${criteria.field.name}`}
//                         label="Select method"
//                         options={options}
//                         value={options[0]}
//                         onChange={(newMethod) => {
//                             const updatedCriteria = blockingCriteria.map((item) =>
//                                 item.field.name === criteria.field.name
//                                     ? { ...item, method: newMethod }
//                                     : item
//                             );
//                             setBlockingCriteria(updatedCriteria);
//                         }}
//                     />
//                 )}
//             />
//             <Icon.Delete size={3} />
//         </div>
//     );
// };
