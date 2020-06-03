import { JoinPointAware } from "./JoinPointAware";
import { JoinPoint } from "./JoinPoint";

export interface JoinPointContext {
    [key: string]: JoinPointAware;
}

declare global {
    interface Window extends JoinPointContext { }
}

export const useJoinPoint = (namespace: string, name: string, point?: JoinPoint): JoinPoint => {
    window[namespace] = window[namespace] || {};
    window[namespace][name] = window[namespace][name] || { ...point };

    const jointPoint = window[namespace][name];
    return jointPoint;
};

export const hasJoinPointId = (id: string): boolean => document.getElementById(id) !== null;
