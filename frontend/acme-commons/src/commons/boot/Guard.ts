import * as _ from "lodash";

export function guard<Result, Callee>(
    object: Callee | null | undefined,
    safe: (object: Callee) => Result,
    fallback?: Result,
): Result | undefined {
    if (!_.isNil(object)) {
        const result = safe(object);
        return result || fallback;
    }
}
