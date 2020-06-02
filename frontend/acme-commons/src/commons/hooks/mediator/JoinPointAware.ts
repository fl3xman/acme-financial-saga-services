import { JoinPoint } from "./JoinPoint";

export interface JoinPointAware {
    [key: string]: JoinPoint
}