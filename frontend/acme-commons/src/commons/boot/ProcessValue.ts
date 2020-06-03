import * as _ from "lodash";

export const processValue = <T = string>(key: string, fallback?: T): string | T | undefined => _.get(process.env, key, fallback);
