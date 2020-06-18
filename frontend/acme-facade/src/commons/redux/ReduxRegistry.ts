import * as _ from "lodash";

import { BehaviorSubject } from "rxjs";
import { Reducer, combineReducers, ReducersMapObject } from "redux";

import { ReduxRegistryAware } from "acme-commons";

export const combineRigistry = <S>(reducers: ReducersMapObject, parentState: S | null): Reducer => {
    const names = _.keys(reducers);
    _.keys(parentState).forEach((name) => {
        if (_.includes(names, name)) {
            reducers[name] = (state: S | null = null) => state;
        }
    });
    return combineReducers(reducers);
};

export class ReduxRegistry implements ReduxRegistryAware {
    constructor(public readonly reducers: BehaviorSubject<ReducersMapObject> = new BehaviorSubject({})) {
        // no-body
    }

    public add(name: string, reducer: Reducer): ReduxRegistryAware {
        const reducers = { ...this.reducers.value, [name]: reducer };
        this.reducers.next(reducers);

        return this;
    }

    public remove(name: string): ReduxRegistryAware {
        const all = this.reducers.value;
        if (_.has(all, name)) {
            delete all[name];
        }

        return this;
    }
}
