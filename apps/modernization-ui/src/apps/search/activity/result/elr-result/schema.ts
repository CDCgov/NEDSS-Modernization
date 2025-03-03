import { Selectable } from 'options';

// export interface ActivityLog {
//   edxActivityLogUid: number;
//   sourceUid: number;
//   targetUid: number;
//   docType: string;
//   recordStatusCd: string;
//   recordStatusTime: string;
//   exceptionTxt: string;
//   impExpIndCd: string;
//   sourceTypeCd: string;
//   targetTypeCd: string;
//   businessObjLocalId: string;
//   docNm: string;
//   sourceNm: string;
//   algorithmAction: string;
//   algorithmName: string;
//   messageId: string;
//   entityNm: string;
//   accessionNbr: string;
// }

export interface ActivityFilter {
  reportType: Selectable;
  eventDateFrom: string;
  eventDateTo: string;
  status: Selectable[];
}