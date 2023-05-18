/**
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

export interface StudentDto { 
    id?: number;
    lastName?: string;
    firstName: string;
    email?: string;
    examAverageGrade?: number;
    birthDate?: string;
    gender?: StudentDto.GenderEnum;
    wantsNewsletter?: boolean;
    addressStreet?: string;
    addressNumber?: string;
    addressCity?: string;
}
export namespace StudentDto {
    export type GenderEnum = 'MALE' | 'FEMALE';
    export const GenderEnum = {
        MALE: 'MALE' as GenderEnum,
        FEMALE: 'FEMALE' as GenderEnum
    };
}