import { Action } from "redux";

import { OrderType } from "./OrderType";
import { OrderState } from "./OrderState";

export const orderReducer = (state = { loading: false }, action: Action<OrderType>): OrderState => {
    switch (action.type) {
        case OrderType.Create:
            return { ...state, loading: true };
        default:
            return state;
    }
};
