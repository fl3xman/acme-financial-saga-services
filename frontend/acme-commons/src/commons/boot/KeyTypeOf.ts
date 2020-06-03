export const keyTypeOf = <T, K extends keyof T>(object: T, key: K): object is T => {
    return key in object;
};
