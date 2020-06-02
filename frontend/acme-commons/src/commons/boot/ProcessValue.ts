import * as _ from "lodash";

export const processValue = (key: string, fallback?: any): any => _.get(process.env, key, fallback);