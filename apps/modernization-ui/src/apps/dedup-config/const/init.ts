export type DataElement = {
    name: string;
    active: boolean;
    m: number;
    u: number;
    threshold: number;
};

export const DataElements: DataElement[] = [
  { name: "lastName", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  { name: "secondLastName", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  { name: "firstName", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  { name: "middleName", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  { name: "secondMiddleName", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  { name: "suffix", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  { name: "currentSex", active: true, m: 0.5, u: 0.5, threshold: 0.5 },
  {
    name: "dateOfBirth",
    active: true,
    m: 0.5,
    u: 0.5,
    threshold: 0.5,
  },
  {
    name: "ssn",
    active: true,
    m: 0.5,
    u: 0.5,
    threshold: 0.5,
  },
  {
    name: "idType",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },
  {
    name: "idAssigningAuthority",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },  
  {
    name: "idValue",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },
  {
    name: "streetAddress1",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },
  {
    name: "city",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },  
  {
    name: "state",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },
  {
    name: "zip",
    active: true,
    m: 0.5,
    u: 0.25,
    threshold: 0.5,
  },
  {
    name: "telephone",
    active: true,
    m: 0.5,
    u: 0.1,
    threshold: 0.5,
  }
];
