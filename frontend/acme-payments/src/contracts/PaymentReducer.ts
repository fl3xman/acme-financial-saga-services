import { combineReducers } from "redux";
import { OrderActionType, orderReducer } from "./orders";

export const paymentReducer = combineReducers({
    [OrderActionType.Name]: orderReducer,
});
