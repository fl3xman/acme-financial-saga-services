import { Action } from "redux";

import { OrderActionType } from "./OrderActionType";
import { OrderState } from "./OrderState";

export const orderReducer = (state = { loading: false }, action: Action<OrderActionType>): OrderState => {
    switch (action.type) {
        case OrderActionType.Create:
            return { ...state, loading: true };
        default:
            return state;
    }
};
