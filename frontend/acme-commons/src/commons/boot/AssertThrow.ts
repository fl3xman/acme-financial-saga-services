import { AssertionError } from "assert";

export function assertThrow(value: boolean, lazyMessage: () => string = () => "Assert error"): asserts value {
    if (!value) {
        throw new AssertionError({ message: lazyMessage() });
    }
}
